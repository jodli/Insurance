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

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteJob;

public class initJob extends SQLiteJob<Object> {

	@Override
	protected Object job(SQLiteConnection connection) throws Throwable {
		// create customer table
		String st = null;
		st = "CREATE TABLE IF NO EXISTS customer ("
				+ "ID Integer PRIMARY KEY AUTOINCREMENT)";
		connection.exec(st);

		// create insurance table
		st = "CREATE TABLE IF NO EXISTS insurance (" + ")";
		connection.exec(st);

		// create employee table
		st = "CREATE TABLE IF NO EXISTS employee (" + ")";
		connection.exec(st);

		return null;
	}

}
