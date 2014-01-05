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
package src.jodli.Client.Utilities.DatabaseJobs;

import src.jodli.Client.log.Logger;

import com.almworks.sqlite4java.SQLiteConnection;

public class InitJob extends JobWrapper<Object> {

	@Override
	protected Object job(SQLiteConnection connection) throws Throwable {
		Logger.logInfo("Creating settings table.");
		String st = null;
		st = "CREATE TABLE IF NOT EXISTS Settings ("
				+ "key Integer PRIMARY KEY," + "value TEXT NOT NULL)";
		connection.exec(st);

		st = "INSERT OR IGNORE INTO Settings (key, value) VALUES (0, '0')";
		connection.exec(st);

		Logger.logInfo("Creating customer table.");
		st = "CREATE TABLE IF NOT EXISTS Customer ("
				+ "ID Integer PRIMARY KEY AUTOINCREMENT)";
		connection.exec(st);

		Logger.logInfo("Creating insurance table.");
		st = "CREATE TABLE IF NOT EXISTS Insurance ("
				+ "ID Integer PRIMARY KEY AUTOINCREMENT)";
		connection.exec(st);

		Logger.logInfo("Creating employee table.");
		st = "CREATE TABLE IF NOT EXISTS Employee ("
				+ "ID Integer PRIMARY KEY AUTOINCREMENT)";
		connection.exec(st);

		// returns new empty object when it finishes successfully.
		return new Object();
	}
}
