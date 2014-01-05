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
package src.jodli.Client.Utilities;

import java.io.File;
import java.time.LocalDate;

import src.jodli.Client.Utilities.DatabaseJobs.InitJob;
import src.jodli.Client.Utilities.DatabaseJobs.Setting;
import src.jodli.Client.Utilities.DatabaseJobs.SettingsJob;
import src.jodli.Client.log.Logger;

import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteQueue;

public class DbUtils {
	private File databaseFile;
	private static final String DB_PATH = "database_"
			+ LocalDate.now().getYear() + ".sqlite";
	private SQLiteQueue queue;
	private static DbUtils instance = null;

	private DbUtils() throws SQLiteException {

		Logger.logInfo("Disabling sqlite4java logging.");
		java.util.logging.Logger.getLogger("com.almworks.sqlite4java")
				.setLevel(java.util.logging.Level.OFF);

		databaseFile = new File(DB_PATH);

		Logger.logInfo("Opening database at path: " + DB_PATH);

		queue = new SQLiteQueue(databaseFile);
		queue.start();
	}

	public static synchronized DbUtils instance() {
		if (instance == null) {
			try {
				instance = new DbUtils();
			} catch (SQLiteException e) {
				Logger.logError(e.getMessage(), e);
			}

			Logger.logInfo("Running initialization job.");
			instance.queue.execute(new InitJob());
		}

		return instance;
	}

	public void dispose() throws InterruptedException {
		if (queue != null) {
			Logger.logInfo("Trying to close database connection.");
			queue.stop(true).join();
		}
	}

	public SQLiteQueue getQueue() {
		return queue;
	}

	public String getVersion() {
		return (String) instance.queue.execute(
				new SettingsJob(Setting.BUILDNUMBER)).complete();
	}
}
