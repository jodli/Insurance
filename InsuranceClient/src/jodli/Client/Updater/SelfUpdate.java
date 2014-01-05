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
package src.jodli.Client.Updater;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import src.jodli.Client.Utilities.DbUtils;
import src.jodli.Client.Utilities.FileUtils;
import src.jodli.Client.Utilities.DatabaseJobs.InsertJob;
import src.jodli.Client.Utilities.DatabaseJobs.Setting;
import src.jodli.Client.Utilities.DatabaseJobs.Table;
import src.jodli.Client.Utilities.DatabaseJobs.UpdateJob;
import src.jodli.Client.log.Logger;

public class SelfUpdate {
	public static void runUpdate(String currentPath,
			String temporaryUpdatePath, String latestBuild) {
		List<String> arguments = new ArrayList<String>();

		String javaPath = System.getProperty("java.home") + File.separator
				+ "bin" + File.separator + "java";

		// path to java executable
		arguments.add(javaPath);
		// sets classpath to SelfUpdate.class
		arguments.add("-cp");
		arguments.add(temporaryUpdatePath);
		arguments.add(SelfUpdate.class.getCanonicalName());
		// add commandline arguments to current path (path of current
		// application) and temp path (path of updated application)
		arguments.add(currentPath);
		arguments.add(temporaryUpdatePath);
		arguments.add(latestBuild);

		ProcessBuilder processUpdate = new ProcessBuilder();
		processUpdate.command(arguments);
		try {
			processUpdate.start();
		} catch (IOException e) {
			Logger.logError(e.getMessage(), e);
		}
		// exit application.
		System.exit(0);
	}

	public static void main(String[] args) {
		// wait a second until application is closed.
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			Logger.logError(e.getMessage(), e);
		}

		String executablePath = args[0];
		String temporaryUpdatePath = args[1];
		String latestBuild = args[2];

		// get old and new application from commandline arguments.
		File executable = new File(executablePath);
		File temporaryUpdate = new File(temporaryUpdatePath);
		// delete old and replace with new application.
		try {
			FileUtils.delete(executable);
			FileUtils.copyFile(temporaryUpdate, executable);
		} catch (IOException e) {
			Logger.logError(e.getMessage(), e);
		}

		// TODO: Add new build number to database after making sure the update
		// was successful.
		if (DbUtils
				.instance()
				.getQueue()
				.execute(
						new InsertJob(Table.SETTINGS, "key, value",
								Setting.BUILDNUMBER.getKey() + ", '"
										+ latestBuild + "'")).complete() == null) {
			DbUtils.instance()
					.getQueue()
					.execute(
							new UpdateJob(Table.SETTINGS, "value='"
									+ latestBuild + "'", "key="
									+ Setting.BUILDNUMBER.getKey()));
		}

		List<String> arguments = new ArrayList<String>();

		String javaPath = System.getProperty("java.home") + File.separator
				+ "bin" + File.separator + "java";

		// path to java executable
		arguments.add(javaPath);
		// sets to run updated jar executable
		arguments.add("-jar");
		arguments.add(executablePath);

		ProcessBuilder processUpdate = new ProcessBuilder();
		processUpdate.command(arguments);

		// start updated application.
		try {
			processUpdate.start();
		} catch (IOException e) {
			Logger.logError(e.getMessage(), e);
		}
	}
}
