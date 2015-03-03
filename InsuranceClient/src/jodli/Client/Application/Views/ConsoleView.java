/*
 *
 *  * Copyright (C) 2013 Jan-Olaf Becker
 *  *
 *  * This program is free software; you can redistribute it and/or
 *  * modify it under the terms of the GNU General Public License
 *  * as published by the Free Software Foundation; either version 2
 *  * of the License, or (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program; if not, write to the Free Software
 *  * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 */

package src.jodli.Client.Application.Views;

import src.jodli.Client.log.*;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Created by job87 on 3/2/2015.
 */
public class ConsoleView implements IView, ILogListener {
    private final static String launcherLogFile = "InsuranceClientLog.txt";
    private JPanel content;
    private JEditorPane displayArea;
    private HTMLEditorKit kit;
    private HTMLDocument doc;
    private LogType logType = LogType.DEBUG;

    private void createUIComponents() {
        displayArea = new JEditorPane("text/html", "");
        displayArea.setEditable(false);
        kit = new HTMLEditorKit();
        displayArea.setEditorKit(kit);

        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane
                .setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

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

    private void $$$setupUI$$$() {
        createUIComponents();
    }

    @Override
    public JComponent getContent() {
        return content;
    }

    synchronized private void refreshLogs() {
        doc = new HTMLDocument();
        displayArea.setDocument(doc);
        java.util.List<LogEntry> entries = Logger.getLogEntries();
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
        switch (entry.m_Level) {
            case ERROR:
                color = "red";
                break;
            case WARN:
                color = "yellow";
            case INFO:
                break;
            case DEBUG:
                color = "gray";
                break;
            case UNKNOWN:
                break;
            default:
                break;
        }
        String ret = entry.toString(logType);
        if (ret == null) {
            return "";
        }
        return "<font color=\""
                + color
                + "\">"
                + (ret.replace("<", "&lt;")
                .replace(">", "&gt;").trim().replace("\r\n", "\n")
                .replace("\n", "<br/>")) + "</font><br/>";
    }

    public void onLogEvent(LogEntry logEntry) {
        addHTML(getMessage(logEntry));
    }

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
}
