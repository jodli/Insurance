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
package src.jodli.Client.Application;

import src.jodli.Client.log.Logger;

/**
 * Main entry point for application. Here's where the magic happens...
 *
 * @author Jan-Olaf Becker
 */
public final class Launcher {

    public static void main(String[] args) {
        // Check if Updater ran.
        String buildNumber = verifyArguments(args);

        logBasicSystemInfo();
        showMainWindow(buildNumber);
    }

    private static void showMainWindow(String buildNumber) {
        Logger.logInfo("Showing main window.");
        MainFrame frm = new MainFrame(buildNumber);
    }

    private static void logBasicSystemInfo() {
        Logger.logInfo("Insurance client starting up.");
        Logger.logDebug("Java version: " + System.getProperty("java.version"));
        Logger.logDebug("Java specification: "
                + System.getProperty("java.vm.specification.name")
                + " version: "
                + System.getProperty("java.vm.specification.version") + " by "
                + System.getProperty("java.vm.specification.vendor"));
        Logger.logDebug("Java vm: " + System.getProperty("java.vm.name")
                + " version: " + System.getProperty("java.vm.version") + " by "
                + System.getProperty("java.vm.vendor"));
        Logger.logDebug("Java home: " + System.getProperty("java.home"));
        Logger.logDebug("OS: " + System.getProperty("os.arch") + " "
                + System.getProperty("os.name") + " "
                + System.getProperty("os.version"));
        Logger.logDebug("User Home Directory: " + System.getProperty("user.home"));
        Logger.logDebug("User Working Directory: " + System.getProperty("user.dir"));

        Logger.logWarn("Test color warning");
        Logger.logError("Test color error");
    }

    /**
     * Verifies command line arguments to check.
     *
     * @param args Command line arguments to be verified.
     * @return If specified: New version in arguments; Else null.
     */
    private static String verifyArguments(String[] args) {
        if (args.length == 1) {
            return args[0];
        }
        return null;
    }
}
