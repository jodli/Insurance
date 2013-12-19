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
package jodli.Client.log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LogEntry {
	private String message = "";
	public LogLevel level = LogLevel.UNKNOWN;
	private Throwable cause;
	private String location;
	private final String dateString;
	private final Date date;
	private static final String dateFormatString = "HH:mm:ss";

	public LogEntry() {
		this.date = new Date();
		this.dateString = new SimpleDateFormat(dateFormatString).format(date);
		this.location = getLocation(cause);
	}

	public LogEntry message(String message) {
		this.message = message;
		if (level == LogLevel.UNKNOWN) {
			message = message.toLowerCase();
			if (message.contains("[severe]") || message.contains("[stderr]")
					|| message.contains("[error]")) {
				level = LogLevel.ERROR;
			} else if (message.contains("[info]")) {
				level = LogLevel.INFO;
			} else if (message.contains("[warning]")
					|| message.contains("[warn]")) {
				level = LogLevel.WARN;
			} else if (message.contains("error") || message.contains("severe")) {
				level = LogLevel.ERROR;
			} else if (message.contains("warn")) {
				level = LogLevel.WARN;
			} else {
				level = LogLevel.INFO;
			}
		}
		return this;
	}

	public LogEntry level(LogLevel level) {
		this.level = level;
		return this;
	}

	public LogEntry cause(Throwable cause) {
		if (cause != this.cause) {
			this.location = getLocation(cause);
		}
		this.cause = cause;
		return this;
	}

	public LogEntry copyInformation(LogEntry entry) {
		this.message = entry.message;
		this.level = entry.level;
		return this;
	}

	public String toString() {
		return toString(LogType.MINIMAL);
	}

	public String toString(LogType type) {
		StringBuilder entryMessage = new StringBuilder();
		if (type.includes(LogType.EXTENDED)) {
			entryMessage.append("[").append(dateString).append("] ");
			entryMessage.append("[").append(level).append("] ");
		}
		if (location != null && type.includes(LogType.EXTENDED)) {
			entryMessage.append(location).append(": ");
		}
		entryMessage.append(message);
		if (cause != null) {
			entryMessage.append(": ").append(cause.toString());
			if (type.includes(LogType.EXTENDED)) {
				for (StackTraceElement stackTraceElement : cause
						.getStackTrace()) {
					entryMessage.append("\n").append(
							stackTraceElement.toString());
				}
			}
		}
		String message = entryMessage.toString();
		return message;
	}

	private static String getLocation(Throwable t) {
		String location = "";
		if (t != null) {
			location += getLocation(t.getStackTrace()) + "->";
		}
		location += getLocation(new Throwable().getStackTrace());
		return location;
	}

	private static String getLocation(StackTraceElement[] stackTraceElements) {
		for (StackTraceElement ste : stackTraceElements) {
			if (!ste.getClassName().equals(Logger.class.getName())
					&& !ste.getClassName().equals(LogEntry.class.getName())) {
				return ste.getClassName().substring(
						ste.getClassName().lastIndexOf('.') + 1)
						+ "." + ste.getMethodName() + ":" + ste.getLineNumber();
			}
		}
		return "unknown location";
	}
}
