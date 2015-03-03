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
import src.jodli.Client.Application.Views.ConsoleView;
import src.jodli.Client.Application.Views.GeneralSettingsView;
import src.jodli.Client.Application.Views.MainTableView;
import src.jodli.Client.Utilities.*;
import src.jodli.Client.Utilities.DatabaseModels.ModelSettings;
import src.jodli.Client.log.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@SuppressWarnings("serial")
public final class MainFrame {

    private String m_BuildNumber;
    private JFrame m_Frame;
    private JTabbedPane m_MainTabbedPane;
    private JTabbedPane m_SettingsTabbedPane;

    private Action m_ExitAction;

    private MainTableView m_TableView;
    private ConsoleView m_ConsoleView;
    private GeneralSettingsView m_GeneralSettingsView;

    public MainFrame(String buildNumber) {
        this.m_BuildNumber = buildNumber;

        initDatabase();
        initFrame();
        initActions();
        initGUIComponents();
        initMainGUI();
        showMainWindow();
        checkUpdate();
    }

    private void initDatabase() {
        // Initialise database connection.
        DatabaseUtils.init();
        // Save new BuildNumber in database or get saved BuildNumber.
        if (m_BuildNumber != null) {
            SettingsUtils.setValue(new ModelSettings(Setting.BUILDNUMBER, m_BuildNumber));
        } else {
            m_BuildNumber = SettingsUtils.getValue(Setting.BUILDNUMBER);
        }
    }

    private void initFrame() {
        Logger.logInfo("Initializing Main Window.");
        m_Frame = new JFrame();
        m_Frame.setSize(new Dimension(800, 600));
        updateTitle();
    }

    private void initActions() {
        Logger.logInfo("Initializing Actions.");
        m_ExitAction = new ExitAction();
    }

    private void initGUIComponents() {
        Logger.logInfo("Initializing GUI Components.");
        m_TableView = new MainTableView();
        m_ConsoleView = new ConsoleView();
        m_GeneralSettingsView = new GeneralSettingsView();
    }

    private void initMainGUI() {
        Logger.logInfo("Initializing Main GUI.");
        m_MainTabbedPane = new JTabbedPane(JTabbedPane.LEFT);
        m_SettingsTabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);

        m_MainTabbedPane.addTab("Tabelle", m_TableView.getContent());
        m_MainTabbedPane.addTab("Einstellungen", m_SettingsTabbedPane);
        m_MainTabbedPane.addTab("Console", m_ConsoleView.getContent());

        m_SettingsTabbedPane.addTab(m_GeneralSettingsView.getTabTitle(), m_GeneralSettingsView.getContent());

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
        m_Frame.setVisible(true);
    }

    private void updateTitle() {
        StringBuilder title = new StringBuilder("Insurance Client v");
        title.append(AppUtils.getVerboseVersion(m_BuildNumber));
        title.append(" - ");
        title.append("database_bla.sqlite");

        // Needs save?
        title.append(" *");

        m_Frame.setTitle(title.toString());
    }

    private JMenuBar getMenuBar() {
        JMenuBar menubar = new JMenuBar();

        JMenu fileMenu = new JMenu("Datei");
        fileMenu.setMnemonic(KeyEvent.VK_D);
        fileMenu.add(m_ExitAction);
        menubar.add(fileMenu);

        JMenu editMenu = new JMenu("Bearbeiten");
        menubar.add(editMenu);

        JMenu infoMenu = new JMenu("Info");
        menubar.add(infoMenu);

        return menubar;
    }

    private void checkUpdate() {
        // Check for update.
        if (Boolean.parseBoolean(SettingsUtils.getValue(Setting.CHECKUPDATE))) {
            UpdateChecker uc = new UpdateChecker(m_BuildNumber);
            if (uc.shouldUpdate()) {
                uc.update();
            }
        }
    }
}
