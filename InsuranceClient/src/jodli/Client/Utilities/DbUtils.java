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

import src.jodli.Client.log.Logger;

import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteQueue;

public class DbUtils {
	private File databaseFile;
	private static final String DB_PATH = "database.sqlite";
	private SQLiteQueue queue;
	private static DbUtils instance = null;

	private DbUtils() {

		Logger.logInfo("Disabling sqlite4java logging.");
		java.util.logging.Logger.getLogger("com.almworks.sqlite4java")
				.setLevel(java.util.logging.Level.OFF);

		databaseFile = new File(DB_PATH);

		Logger.logInfo("Creating / Opening database at path: " + DB_PATH);

		queue = new SQLiteQueue(databaseFile);
		queue.start();
	}

	public static synchronized DbUtils get() throws SQLiteException {
		if (instance == null) {
			instance = new DbUtils();

			Logger.logInfo("Running initialization job.");
			instance.queue.execute(new initJob());
		}

		return instance;
	}

	public void dispose() throws InterruptedException {
		if (queue != null) {
			Logger.logInfo("Trying to close database connection.");
			queue.stop(true).join();
		}
	}
}
