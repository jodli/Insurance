/**
 * 
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

		String sql = "INSERT INTO " + this.table.toString() 
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
