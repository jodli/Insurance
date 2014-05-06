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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;

import src.jodli.Client.Updater.MainConsole;
import src.jodli.Client.TableModels.InsureeTableModel;
import src.jodli.Client.log.Logger;

@SuppressWarnings("serial")
public final class MainFrame extends JFrame {

    public static JPanel panel;
    private MainConsole console;
    private JTable table;
    private JTabbedPane tabbedPane;
    private MenuBar menuBar;
    private Menu menuFile;

    private final String version;

    public MainFrame(String version) {
        this.version = version;
        initComponents();
    }

    private void initComponents() {
        this.setVisible(false);

        setTitle("Insurance Client v" + version);
        setMinimumSize(new Dimension(800, 400));

        panel = new JPanel();

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        panel.setLayout(new BorderLayout());

        tabbedPane = new JTabbedPane(JTabbedPane.LEFT);

        table = new JTable(new InsureeTableModel());
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        table.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = ((JTable) e.getSource()).getSelectedRow();
                    int id = ((InsureeTableModel) table.getModel()).getId(row);
                    Logger.logInfo("Double Click on row: " + row + " with ID: " + id);
                    new InsureeFrame(id).setVisible(true);
                }
            }
        });

        tabbedPane.addTab("Table", new JScrollPane(table));

        console = new MainConsole();
        tabbedPane.addTab("Console", console);
        panel.add(tabbedPane);

        getContentPane().add(panel);
        setMenuBar(menuBar);

        pack();
        this.setLocationRelativeTo(null);
        this.setSize(566, 40);
    }

    private void initMenu() {
        menuBar = new MenuBar();

        menuFile = new Menu("File");

        menuFile.add(new MenuItem("Quit"));
        menuFile.getItem(0).addActionListener((ActionEvent arg0) -> {
            Logger.logInfo("Quit menu item pressed");
        });

        menuBar.add(menuFile);
    }

    public void showFrame() {
        this.setVisible(true);
    }
}
