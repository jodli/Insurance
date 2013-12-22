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
package jodli.Client.Application;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

import jodli.Client.Updater.MainConsole;
import jodli.Client.log.Logger;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	public static JPanel panel;
	private MainConsole console;
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

		menuBar = new MenuBar();
		menuFile = new Menu("File");
		menuFile.add(new MenuItem("Quit"));
		menuFile.getItem(0).addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				Logger.logInfo("Quit menu item pressed");
			}
		});
		menuBar.add(menuFile);

		panel = new JPanel();

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		panel.setBounds(0, 0, 850, 480);
		panel.setLayout(new BorderLayout());

		tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		tabbedPane.setBounds(0, 0, 850, 380);

		console = new MainConsole();
		tabbedPane.add(console);
		panel.add(tabbedPane);

		getContentPane().add(panel);
		setMenuBar(menuBar);

		pack();
		this.setSize(566, 40);
	}

	public void showFrame() {
		this.setVisible(true);
	}
}
