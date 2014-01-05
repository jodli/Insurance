/**
 * 
 */
package src.jodli.Client.Utilities.DatabaseJobs;

public enum Setting {
	BUILDNUMBER(0);

	private int Key;

	private Setting(int key) {
		this.Key = key;
	}

	public int getKey() {
		return this.Key;
	}

	@Override
	public String toString() {
		String s = super.toString();
		return s.substring(0, 1) + s.substring(1).toLowerCase();
	};
}
