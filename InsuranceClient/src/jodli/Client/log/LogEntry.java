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

import java.text.SimpleDateFormat;
import java.util.Date;

public class LogEntry {
    private static final String m_DateFormatString = "HH:mm:ss";
    private final String m_DateString;
    private final Date m_Date;
    public LogLevel m_Level = LogLevel.UNKNOWN;
    private String m_Message = "";
    private Throwable m_Cause;
    private String m_Location;

    public LogEntry() {
        this.m_Date = new Date();
        this.m_DateString = new SimpleDateFormat(m_DateFormatString).format(m_Date);
        this.m_Location = getLocation(m_Cause);
    }

    public LogEntry(LogLevel level, String message, Throwable cause) {
        this();
        this.setLevel(level);
        this.setMessage(message);
        this.setCause(cause);
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

    public void setMessage(String message) {
        this.m_Message = message;
        if (m_Level == LogLevel.UNKNOWN) {
            message = message.toLowerCase();
            if (message.contains("[severe]") || message.contains("[stderr]")
                    || message.contains("[error]")) {
                m_Level = LogLevel.ERROR;
            } else if (message.contains("[info]")) {
                m_Level = LogLevel.INFO;
            } else if (message.contains("[warning]")
                    || message.contains("[warn]")) {
                m_Level = LogLevel.WARN;
            } else if (message.contains("error") || message.contains("severe")) {
                m_Level = LogLevel.ERROR;
            } else if (message.contains("warn")) {
                m_Level = LogLevel.WARN;
            } else {
                m_Level = LogLevel.INFO;
            }
        }
    }

    public void setLevel(LogLevel level) {
        this.m_Level = level;
    }

    public void setCause(Throwable cause) {
        if (cause != this.m_Cause) {
            this.m_Location = getLocation(cause);
        }
        this.m_Cause = cause;
    }

    public LogEntry copyInformation(LogEntry entry) {
        this.m_Message = entry.m_Message;
        this.m_Level = entry.m_Level;
        return this;
    }

    public String toString() {
        return toString(LogType.MINIMAL);
    }

    public String toString(LogType type) {
        StringBuilder entryMessage = new StringBuilder();

        // Always display Time.
        entryMessage.append("[").append(m_DateString).append("] ");

        // Display LogLevel in EXTENDED.
        if (type.includes(LogType.EXTENDED)) {
            entryMessage.append("[").append(m_Level).append("] ");
        }

        // Display Location when in DEBUG.
        if (m_Location != null && type.includes(LogType.DEBUG)) {
            entryMessage.append(m_Location).append(": ");
        }

        // Do not display logDebug when in MINIMAL.
        if (type == LogType.MINIMAL && m_Level == LogLevel.DEBUG) {
            return null;
        }

        entryMessage.append(m_Message);

        // Display cause when in EXTENDED.
        if (m_Cause != null && type.includes(LogType.EXTENDED)) {
            entryMessage.append(": ").append(m_Cause.toString());
            // Display StackTrace when in DEBUG.
            if (type.includes(LogType.DEBUG)) {
                for (StackTraceElement stackTraceElement : m_Cause
                        .getStackTrace()) {
                    entryMessage.append("\n").append(
                            stackTraceElement.toString());
                }
            }
        }

        String message = entryMessage.toString();
        return message;
    }
}
