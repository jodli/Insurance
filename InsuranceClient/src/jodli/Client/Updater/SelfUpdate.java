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
package jodli.Client.Updater;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jodli.Client.Utilities.FileUtils;

public class SelfUpdate {
	public static void runUpdate(String currentPath, String temporaryUpdatePath) {
		List<String> arguments = new ArrayList<String>();

		String separator = System.getProperty("file.separator");
		String javaPath = System.getProperty("java.home") + separator + "bin"
				+ separator + "java";

		arguments.add(javaPath);
		arguments.add("-cp");
		arguments.add(temporaryUpdatePath);
		arguments.add(SelfUpdate.class.getCanonicalName());
		arguments.add(currentPath);
		arguments.add(temporaryUpdatePath);

		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.command(arguments);
		try {
			processBuilder.start();
		} catch (IOException e) {
		}
		System.exit(0);
	}

	public static void main(String[] args) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException ignored) {
		}

		String launcherPath = args[0];
		String temporaryUpdatePath = args[1];

		File launcher = new File(launcherPath);
		File temporaryUpdate = new File(temporaryUpdatePath);
		try {
			FileUtils.delete(launcher);
			FileUtils.copyFile(temporaryUpdate, launcher);
		} catch (IOException e) {
		}

		List<String> arguments = new ArrayList<String>();

		String separator = System.getProperty("file.separator");
		String javaPath = System.getProperty("java.home") + separator + "bin"
				+ separator + "java";

		arguments.add(javaPath);
		arguments.add("-jar");
		arguments.add(launcherPath);

		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.command(arguments);

		try {
			processBuilder.start();
		} catch (IOException e) {
		}
	}
}
