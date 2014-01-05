/**
 * 
 */
package src.jodli.Client.Utilities.DatabaseJobs;

public enum Table {
	SETTINGS, CUSTOMER, INSURANCE, EMPLOYEE;

	@Override
	public String toString() {
		String s = super.toString();
		return s.substring(0, 1) + s.substring(1).toLowerCase();
	};
}
