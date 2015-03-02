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
package src.jodli.Client.log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class LogThread extends Thread {
    private BlockingQueue<LogEntry> logQueue = new LinkedBlockingQueue<LogEntry>();
    private List<ILogListener> listeners;

    public LogThread(List<ILogListener> listeners) {
        this.listeners = listeners;
        this.setDaemon(true);
    }

    public void run() {
        LogEntry entry;
        try {
            while ((entry = logQueue.take()) != null) {
                if (listeners.isEmpty()) {
                    (entry.m_Level == LogLevel.ERROR ? System.err : System.out)
                            .println(entry.toString(LogType.DEBUG));
                } else {
                    List<ILogListener> tempListeners = new ArrayList<ILogListener>();
                    tempListeners.addAll(listeners);
                    for (ILogListener listener : tempListeners) {
                        listener.onLogEvent(entry);
                    }
                }
            }
        } catch (InterruptedException ignored) {
        }
    }

    public void handleLog(LogEntry logEntry) {
        try {
            logQueue.put(logEntry);
        } catch (InterruptedException ignored) {
            Logger.logError(ignored.getMessage(), ignored);
        }
    }
}
