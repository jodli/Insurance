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

import java.awt.Cursor;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import jodli.Client.Utilities.OSUtils;
import jodli.Client.Utilities.OSUtils.OS;

import com.sun.imageio.plugins.common.I18N;
import com.sun.scenario.Settings;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	public static JPanel panel;
	private JPanel footer = new JPanel();
	private JButton launch = new JButton(), edit = new JButton(),
			donate = new JButton(), serverbutton = new JButton(),
			mapInstall = new JButton(), serverMap = new JButton(),
			tpInstall = new JButton();

	private static MainFrame instance = null;
	private final String version;

	public final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);

	public MainFrame(String version) {
		this.version = version;
		initComponents();
	}

	private void initComponents() {
		this.setVisible(false);

		setFont(new Font("a_FuturaOrto", Font.PLAIN, 12));
		setResizable(false);
		setTitle("Insurance Client v" + version);

		panel = new JPanel();

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		if (OSUtils.getCurrentOS() == OS.WINDOWS) {
			setBounds(100, 100, 842, 480);
		} else {
			setBounds(100, 100, 850, 480);
		}
		panel.setBounds(0, 0, 850, 480);
		panel.setLayout(null);
		footer.setBounds(0, 380, 850, 100);
		footer.setLayout(null);
		tabbedPane.setBounds(0, 0, 850, 380);
		panel.add(tabbedPane);
		panel.add(footer);
		setContentPane(panel);

		footer.add(edit);
		footer.add(launch);
		footer.add(donate);
		footer.add(serverbutton);
		footer.add(mapInstall);
		footer.add(serverMap);
		footer.add(tpInstall);

		getRootPane().setDefaultButton(launch);

		pack();
		this.setSize(800, 600);
	}

	public void showFrame() {
		this.setVisible(true);
	}
}
