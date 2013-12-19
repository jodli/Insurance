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
package jodli.Client.Updater;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URI;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import jodli.Client.log.ILogListener;
import jodli.Client.log.LogEntry;
import jodli.Client.log.LogLevel;
import jodli.Client.log.LogType;
import jodli.Client.log.LogWriter;
import jodli.Client.log.Logger;

import com.sun.scenario.Settings;

@SuppressWarnings("serial")
public class MainConsole extends JFrame implements ILogListener {
	private final static String launcherLogFile = "FTBLauncherLog.txt";
	private final static String minecraftLogFile = "MinecraftLog.txt";
	private JEditorPane displayArea;
	private HTMLEditorKit kit;
	private HTMLDocument doc;
	private JComboBox<LogType> logTypeComboBox;
	private LogType logType = LogType.DEBUG;

	private class OutputOverride extends PrintStream {
		final LogLevel level;

		public OutputOverride(OutputStream str, LogLevel type) {
			super(str);
			this.level = type;
		}

		@Override
		public void write(byte[] b) throws IOException {
			super.write(b);
			String text = new String(b).trim();
			if (!text.equals("") && !text.equals("\n")) {
				Logger.log("From Console: " + text, level, null);
			}
		}

		@Override
		public void write(byte[] buf, int off, int len) {
			super.write(buf, off, len);
			String text = new String(buf, off, len).trim();
			if (!text.equals("") && !text.equals("\n")) {
				Logger.log("From Console: " + text, level, null);
			}
		}

		@Override
		public void write(int b) {
			throw new UnsupportedOperationException(
					"Write(int) is not supported by OutputOverride.");
		}
	}

	public MainConsole() {
		setTitle("Console");
		setMinimumSize(new Dimension(800, 400));
		getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();

		getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		JButton pastebin = new JButton("Paste my log to pastebin.com");
		pastebin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane pane = new JOptionPane(
						"The log will be copied to your clipboard and pastebin.com will be opened now");
				Object[] options = new String[] { "Yes do it", "Cancel" };
				pane.setOptions(options);
				JDialog dialog = pane.createDialog(new JFrame(),
						"Paste to pastebin.com");
				dialog.setVisible(true);
				Object obj = pane.getValue();
				int result = -1;
				for (int i = 0; i < options.length; i++) {
					if (options[i].equals(obj)) {
						result = i;
					}
				}
				if (result == 0) {
					// PastebinPoster thread = new PastebinPoster();
					// thread.start();
				}
			}
		});
		panel.add(pastebin);

		logTypeComboBox = new JComboBox<LogType>(LogType.values());
		logTypeComboBox.setSelectedItem(logType);
		logTypeComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				logType = (LogType) logTypeComboBox.getSelectedItem();
				refreshLogs();
			}
		});
		panel.add(logTypeComboBox);

		displayArea = new JEditorPane("text/html", "");
		displayArea.setEditable(false);
		kit = new HTMLEditorKit();
		displayArea.setEditorKit(kit);

		JScrollPane scrollPane = new JScrollPane(displayArea);
		scrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		getContentPane().add(scrollPane);

		refreshLogs();
		Logger.addListener(this);

		System.setOut(new OutputOverride(System.out, LogLevel.INFO));
		System.setErr(new OutputOverride(System.err, LogLevel.ERROR));
		try {
			Logger.addListener(new LogWriter(new File(launcherLogFile)));
		} catch (IOException e1) {
			Logger.logError(e1.getMessage(), e1);
		}
	}

	public void showFrame() {
		this.setVisible(true);
	}

	synchronized private void refreshLogs() {
		doc = new HTMLDocument();
		displayArea.setDocument(doc);
		List<LogEntry> entries = Logger.getLogEntries();
		StringBuilder logHTML = new StringBuilder();
		for (LogEntry entry : entries) {
			logHTML.append(getMessage(entry));
		}
		addHTML(logHTML.toString());
	}

	private void addHTML(String html) {
		synchronized (kit) {
			try {
				kit.insertHTML(doc, doc.getLength(), html, 0, 0, null);
			} catch (BadLocationException ignored) {
				Logger.logError(ignored.getMessage(), ignored);
			} catch (IOException ignored) {
				Logger.logError(ignored.getMessage(), ignored);
			}
			displayArea.setCaretPosition(displayArea.getDocument().getLength());
		}
	}

	private String getMessage(LogEntry entry) {
		String color = "black";
		switch (entry.level) {
		case ERROR:
			color = "#FF7070";
			break;
		case WARN:
			color = "yellow";
		case INFO:
			break;
		case UNKNOWN:
			break;
		default:
			break;
		}
		return "<font color=\""
				+ color
				+ "\">"
				+ (entry.toString(logType).replace("<", "&lt;")
						.replace(">", "&gt;").trim().replace("\r\n", "\n")
						.replace("\n", "<br/>")) + "</font><br/>";
	}

	public void onLogEvent(LogEntry logEntry) {
		addHTML(getMessage(logEntry));
	}

}
