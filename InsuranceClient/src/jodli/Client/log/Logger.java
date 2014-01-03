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
import java.util.Vector;

public class Logger {
	private static final List<ILogListener> listeners;
	private static final Vector<LogEntry> logEntries = new Vector<LogEntry>();
	private static LogThread logThread;

	static {
		listeners = new ArrayList<ILogListener>();
		logThread = new LogThread(listeners);
		logThread.start();
	}

	public static void log(LogEntry entry) {
		logEntries.add(entry);
		logThread.handleLog(entry);
	}

	public static void log(String message, LogLevel level, Throwable t) {
		log(new LogEntry().level(level).message(message).cause(t));
	}

	public static void logInfo(String message) {
		logInfo(message, null);
	}

	public static void logWarn(String message) {
		logWarn(message, null);
	}

	public static void logError(String message) {
		logError(message, null);
	}

	public static void logInfo(String message, Throwable t) {
		log(message, LogLevel.INFO, t);
	}

	public static void logWarn(String message, Throwable t) {
		log(message, LogLevel.WARN, t);
	}

	public static void logError(String message, Throwable t) {
		log(message, LogLevel.ERROR, t);
	}

	public static void addListener(ILogListener listener) {
		listeners.add(listener);
	}

	public static void removeListener(ILogListener listener) {
		listeners.remove(listener);
	}

	public static List<LogEntry> getLogEntries() {
		return new Vector<LogEntry>(logEntries);
	}

	public static String getLogs() {
		return getLogs(LogType.EXTENDED);
	}

	private static String getLogs(LogType type) {
		StringBuilder logStringBuilder = new StringBuilder();
		for (LogEntry entry : logEntries) {
			logStringBuilder.append(entry.toString(type)).append("\n");
		}
		return logStringBuilder.toString();
	}
}
