/**
 * 
 */
package src.jodli.Client.Utilities.DatabaseJobs;


public class SettingsJob extends SelectJob {

	public SettingsJob(Setting s) {
		super(Table.SETTINGS, "value", "key = " + s.getKey());
	}
}
