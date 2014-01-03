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

import src.jodli.Client.log.Logger;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteJob;

public class initJob extends SQLiteJob<Object> {

	@Override
	protected Object job(SQLiteConnection connection) throws Throwable {
		Logger.logInfo("Creating customer table");
		// create customer table
		String st = null;
		st = "CREATE TABLE IF NOT EXISTS customer ("
				+ "ID Integer PRIMARY KEY AUTOINCREMENT)";
		connection.exec(st);

		// create insurance table
		st = "CREATE TABLE IF NOT EXISTS insurance (" + ")";
		connection.exec(st);

		// create employee table
		st = "CREATE TABLE IF NOT EXISTS employee (" + ")";
		connection.exec(st);

		return new Object();
	}

	@Override
	protected void jobStarted(SQLiteConnection connection) throws Throwable {
		Logger.logInfo(this.toString() + " started.");
		super.jobStarted(connection);
	}

	@Override
	protected void jobFinished(Object result) throws Throwable {
		if (result == null) {
			Logger.logError(this.toString() + " finished with an error.",
					this.getError());
		} else {
			Logger.logInfo(this.toString() + " finished.");
		}
		super.jobFinished(result);
	}
}
