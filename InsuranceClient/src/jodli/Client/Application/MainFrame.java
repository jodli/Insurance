/*
 * Copyright (C) 2013 Jan-Olaf Becker
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package src.jodli.Client.Application;

import src.jodli.Client.Actions.ExitAction;
import src.jodli.Client.Actions.NewOpenAction;
import src.jodli.Client.Actions.SettingsAction;
import src.jodli.Client.Application.Views.*;
import src.jodli.Client.TableModels.EmployeeTableModel;
import src.jodli.Client.TableModels.InsuranceTableModel;
import src.jodli.Client.TableModels.InsureeTableModel;
import src.jodli.Client.Updater.UpdateChecker;
import src.jodli.Client.Utilities.*;
import src.jodli.Client.log.ELogType;
import src.jodli.Client.log.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

@SuppressWarnings("serial")
public final class MainFrame implements Observer {

    private String m_BuildNumber;
    private JFrame m_Frame;
    private JTabbedPane m_MainTabbedPane;

    private Action m_OpenAction;
    private Action m_SettingsAction;
    private Action m_ExitAction;

    private IView m_InsureeTableView;
    private IView m_InsuranceTableView;
    private IView m_EmployeeTableView;

    private ConsoleView m_ConsoleView;
    private ISettingsView m_GeneralSettingsView;

    public MainFrame(String buildNumber) {
        this.m_BuildNumber = buildNumber;

        initFrame();
        initDatabase();
        initGUIComponents();
        initActions();
        initMainGUI();
        showMainWindow();
    }

    private void initDatabase() {
        Logger.logDebug("Initializing database connection");

        if (m_BuildNumber != null) {
            SettingsUtils.setValue(ESetting.BUILDNUMBER, m_BuildNumber);
        } else {
            m_BuildNumber = SettingsUtils.getValue(ESetting.BUILDNUMBER);
        }

        DatabaseUtils.getInstance().addObserver(this);
        DatabaseUtils.getInstance().addObserver(InsureeUtils.getInstance());

        DatabaseUtils.openDatabase();

        InsureeUtils.getInstance().addObserver(this);
    }

    private void initFrame() {
        Logger.logDebug("Initializing Main Window.");

        m_Frame = new JFrame();
        m_Frame.setSize(new Dimension(800, 600));
    }

    private void initGUIComponents() {
        Logger.logDebug("Initializing GUI Components.");

        m_InsureeTableView = new InsureeTableView(new InsureeTableModel());
        m_InsureeTableView.addObserver(this);
        m_InsuranceTableView = new InsuranceTableView(new InsuranceTableModel());
        m_EmployeeTableView = new EmployeeTableView(new EmployeeTableModel());
        m_ConsoleView = new ConsoleView();
        m_GeneralSettingsView = new GeneralSettingsView();
        m_GeneralSettingsView.addObserver(this);
    }

    private void initActions() {
        Logger.logDebug("Initializing Actions.");
        java.util.List<ISettingsView> settingsViews = new ArrayList<>();
        settingsViews.add(m_GeneralSettingsView);

        m_OpenAction = new NewOpenAction(m_Frame);
        m_SettingsAction = new SettingsAction(m_Frame, settingsViews);
        m_ExitAction = new ExitAction();
    }

    private void initMainGUI() {
        Logger.logDebug("Initializing Main GUI.");
        m_MainTabbedPane = new JTabbedPane(JTabbedPane.LEFT);

        m_MainTabbedPane.addTab(m_InsureeTableView.getTabTitle(), m_InsureeTableView.getContent());
        m_MainTabbedPane.addTab(m_InsuranceTableView.getTabTitle(), m_InsuranceTableView.getContent());
        m_MainTabbedPane.addTab(m_EmployeeTableView.getTabTitle(), m_EmployeeTableView.getContent());
        m_MainTabbedPane.addTab(m_ConsoleView.getTabTitle(), m_ConsoleView.getContent());

        m_Frame.getContentPane().add(m_MainTabbedPane);
        m_Frame.setJMenuBar(getMenuBar());

        m_Frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                m_ExitAction.actionPerformed(null);
            }
        });
    }

    private void showMainWindow() {
        centerWindow();
        m_Frame.setVisible(true);
    }

    private void centerWindow() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension window = m_Frame.getSize();

        if (window.height > screen.height) {
            window.height = screen.height;
        }
        if (window.width > screen.width) {
            window.width = screen.width;
        }
        int x = screen.width / 2 - window.width / 2;
        int y = screen.height / 2 - window.height / 2;

        m_Frame.setLocation(x, y);
    }

    private void updateTitle() {
        Logger.logDebug("Updating title.");
        StringBuilder title = new StringBuilder("Insurance Client v");
        title.append(AppUtils.getVerboseVersion(m_BuildNumber));
        title.append(" - ");
        title.append(new File(SettingsUtils.getValue(ESetting.LASTDATABASE)).getName());

        m_Frame.setTitle(title.toString());
    }

    private JMenuBar getMenuBar() {
        JMenuBar menubar = new JMenuBar();

        JMenu fileMenu = new JMenu("Datei");
        fileMenu.setMnemonic(KeyEvent.VK_D);
        fileMenu.add(m_OpenAction);
        fileMenu.add(m_SettingsAction);
        fileMenu.add(m_ExitAction);
        menubar.add(fileMenu);

        JMenu editMenu = new JMenu("Bearbeiten");
        menubar.add(editMenu);

        JMenu infoMenu = new JMenu("Info");
        menubar.add(infoMenu);

        return menubar;
    }

    private void enableInsuranceTab() {
        m_MainTabbedPane.setEnabledAt(1, true);
    }

    private void disableInsuranceTab() {
        m_MainTabbedPane.setEnabledAt(1, false);
    }

    @Override
    public void update(Observable o, Object arg) {
        Logger.logDebug("Receiving updates.");
        if (o == m_GeneralSettingsView) {
            Logger.logDebug("Notified by General Settings View.");
            // updating LogType
            ((ConsoleView) m_ConsoleView).setLogType(ELogType.valueOf(SettingsUtils.getValue(ESetting.LOGTYPE)));

            // updating UpdateCheck
            UpdateChecker.updateApp();
        } else if (o == DatabaseUtils.getInstance()) {
            Logger.logDebug("Notified by Database Utilities.");
            updateTitle();
        } else if (o == InsureeUtils.getInstance()) {
            Logger.logDebug("Notified by Insuree Utilities.");
        } else if (o == m_InsureeTableView) {
            Logger.logDebug("Notified by Insuree Table View.");
            ((InsuranceTableView) m_InsuranceTableView).update(((Integer) arg));
            enableInsuranceTab();
        }
    }
}
