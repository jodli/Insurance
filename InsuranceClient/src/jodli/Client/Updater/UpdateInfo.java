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
package src.jodli.Client.Updater;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;

import src.jodli.Client.Application.App;
import src.jodli.Client.Utilities.AppUtils;
import src.jodli.Client.Utilities.DownloadTask;
import src.jodli.Client.Utilities.OSUtils;
import src.jodli.Client.log.Logger;

@SuppressWarnings("serial")
public class UpdateInfo extends JFrame implements PropertyChangeListener {
	private JEditorPane infoPane;
	private JScrollPane scp;
	private JButton ok;
	private JButton cancel;
	private JPanel pan1;
	private JPanel pan2;
	private JProgressBar progbar;

	private String downloadAddress;
	private String latestBuildNumber;
	private String title;
	private DownloadTask task;

	public UpdateInfo(String changelog, String downloadAddress,
			String latestBuild) {
		this.downloadAddress = downloadAddress;
		this.latestBuildNumber = latestBuild;
		this.title = "New Update Found ver "
				+ AppUtils.getVersion(latestBuildNumber);

		initComponents();

		infoPane.setText(changelog);
	}

	private void initComponents() {

		this.setVisible(false);

		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		this.setTitle(title);
		pan1 = new JPanel();
		pan1.setLayout(new BorderLayout());

		pan2 = new JPanel();
		pan2.setLayout(new FlowLayout());

		infoPane = new JEditorPane();
		infoPane.setContentType("text/html");

		scp = new JScrollPane();
		scp.setViewportView(infoPane);

		progbar = new JProgressBar(0, 100);
		progbar.setStringPainted(true);

		ok = new JButton("Download Update");

		ok.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				update();
			}
		});

		cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (UpdateInfo.this.task != null) {
					UpdateInfo.this.task.cancel(true);
				}
				UpdateInfo.this.dispose();
			}
		});
		pan2.add(ok);
		pan2.add(cancel);
		pan1.add(progbar, BorderLayout.NORTH);
		pan1.add(pan2, BorderLayout.SOUTH);
		pan1.add(scp, BorderLayout.CENTER);
		this.add(pan1);

		pack();
		this.setLocationRelativeTo(null);
		this.setSize(300, 200);
	}

	public void showFrame() {
		this.setVisible(true);
	}

	private void update() {

		String path = null;
		try {
			path = new File(App.class.getProtectionDomain().getCodeSource()
					.getLocation().getPath()).getCanonicalPath();
			path = URLDecoder.decode(path, "UTF-8");
		} catch (IOException e) {
			Logger.logError("Couldn't get path to current Application.", e);
		}

		String temporaryUpdatePath = OSUtils.getDynamicStorageLocation()
				+ "updatetemp" + File.separator;
		Logger.logInfo("Temporary update path: " + temporaryUpdatePath);
		try {
			progbar.setValue(0);
			this.ok.setEnabled(false);
			task = new DownloadTask(this, new URL(downloadAddress),
					temporaryUpdatePath, path, latestBuildNumber);
			task.addPropertyChangeListener(this);
			task.execute();

		} catch (Exception e) {
			Logger.logError(e.getMessage(), e);
			// show messagebox
			UpdateInfo.this.dispose();
		}
	}

	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("progress")) {
			int progress = (Integer) evt.getNewValue();
			progbar.setValue(progress);
		}
	}
}
