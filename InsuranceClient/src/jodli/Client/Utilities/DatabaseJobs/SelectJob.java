/**
 * 
 */
package src.jodli.Client.Utilities.DatabaseJobs;

import src.jodli.Client.log.Logger;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteStatement;

public class SelectJob extends JobWrapper<Object> {

	private Table table;
	private String row = "";
	private String where = "";

	public SelectJob(Table t, String r) {
		this.table = t;
		this.row = r;
	}

	public SelectJob(Table t, String r, String w) {
		this(t, r);
		this.where = w;
	}

	@Override
	protected Object job(SQLiteConnection connection) throws Throwable {

		String ret = "";
		String sql = "SELECT " + this.row + " FROM " + this.table.toString();

		if (this.where.length() > 0) {
			sql += " WHERE " + this.where;
		}

		Logger.logInfo(sql);

		SQLiteStatement st = connection.prepare(sql, false);

		try {
			while (st.step()) {
				ret = st.columnString(0);
			}
		} finally {
			st.dispose();
		}

		return ret;
	}
}
