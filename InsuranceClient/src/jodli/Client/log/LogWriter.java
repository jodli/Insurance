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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class LogWriter implements ILogListener {
	private final BufferedWriter logWriter;

	public LogWriter(File logFile) throws IOException {
		this.logWriter = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(logFile), "UTF-8"));
	}

	public void onLogEvent(LogEntry entry) {
		try {
			logWriter.write(entry.toString(LogType.EXTENDED)
					+ System.getProperty("line.separator"));
			logWriter.flush();
		} catch (IOException e) {
			Logger.logError(e.getMessage(), e);
		}
	}
}
