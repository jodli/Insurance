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
import com.almworks.sqlite4java.SQLiteStatement;

public class InsertJob extends JobWrapper<Object> {

	private Table table;
	private String columns = "";
	private String values = "";

	public InsertJob(Table t, String c, String v) {
		this.table = t;
		this.columns = c;
		this.values = v;
	}

	@Override
	protected Object job(SQLiteConnection connection) throws Throwable {

		String sql = "INSERT OR ABORT INTO " + this.table.toString() 
		+ "(" + this.columns + ") VALUES " 
		+ "(" + this.values + ")";
		
		Logger.logInfo(sql);
		
		SQLiteStatement st = connection.prepare(sql, false);

		try {
			st.stepThrough();
		} finally {
			st.dispose();
		}
		return new Object();
	}

}
