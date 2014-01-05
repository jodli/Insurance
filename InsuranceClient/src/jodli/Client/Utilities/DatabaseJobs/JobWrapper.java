/**
 * 
 */
package src.jodli.Client.Utilities.DatabaseJobs;

import src.jodli.Client.log.Logger;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteJob;

public abstract class JobWrapper<T> extends SQLiteJob<T> {

	@Override
	protected void jobStarted(SQLiteConnection connection) throws Throwable {
		Logger.logInfo(getClass().getSimpleName() + " started.");
		super.jobStarted(connection);
	}

	@Override
	protected void jobFinished(T result) throws Throwable {
		if (result == null) {
			Logger.logError(getClass().getSimpleName()
					+ " finished with an error.", this.getError());
		} else {
			Logger.logInfo(getClass().getSimpleName() + " finished.");
		}
		super.jobFinished(result);
	}
}
