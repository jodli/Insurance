/**
 * 
 */
package src.jodli.Client.Utilities.DatabaseJobs;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteStatement;

public class selectJob extends JobWrapper<Object> {

	private String Table = "";
	private String Row = "";

	public selectJob(String table, String row) {
		this.Table = table;
		this.Row = row;
	}

	@Override
	protected Object job(SQLiteConnection connection) throws Throwable {

		SQLiteStatement st = connection.prepare("SELECT " + this.Row + " FROM "
				+ this.Table, false);

		try {
			while (st.step()) {
				System.out.println(st.columnCount());
			}
		} finally {
			st.dispose();
		}

		return null;
	}
}
