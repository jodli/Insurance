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

import src.jodli.Client.Utilities.*;
import src.jodli.Client.Utilities.DatabaseModels.ModelSettings;
import src.jodli.Client.log.Logger;

import java.awt.*;

/**
 * Main entry point for application. Here's where the magic happens...
 *
 * @author Jan-Olaf Becker
 */
public class App {

    private static String buildNumber = null;
    private static String version;

    public static void main(String[] args) {
        if (verifyArguments(args)) {
            buildNumber = args[0];
        }

        EventQueue.invokeLater(() -> {
            DatabaseUtils.init();
            if (buildNumber != null) {
                SettingsUtils.setValue(new ModelSettings(
                        Setting.BUILDNUMBER, buildNumber));
            }

            buildNumber = SettingsUtils.getValue(Setting.BUILDNUMBER);
            version = AppUtils.getVersion(buildNumber);

            MainFrame frm = new MainFrame(version);

            frm.showFrame();

            UpdateChecker uc = new UpdateChecker(buildNumber);

            if (Boolean.parseBoolean(SettingsUtils.getValue(Setting.CHECKUPDATE))) {
                if (uc.shouldUpdate()) {
                    uc.update();
                }
            }
        });

        Logger.logInfo("Insurance client starting up (Version " + version + ")");
        Logger.logInfo("Java version: " + System.getProperty("java.version"));
        Logger.logInfo("Java vendor: " + System.getProperty("java.vendor"));
        Logger.logInfo("Java home: " + System.getProperty("java.home"));
        Logger.logInfo("Java specification: "
                + System.getProperty("java.vm.specification.name")
                + " version: "
                + System.getProperty("java.vm.specification.version") + " by "
                + System.getProperty("java.vm.specification.vendor"));
        Logger.logInfo("Java vm: " + System.getProperty("java.vm.name")
                + " version: " + System.getProperty("java.vm.version") + " by "
                + System.getProperty("java.vm.vendor"));
        Logger.logInfo("OS: " + System.getProperty("os.arch") + " "
                + System.getProperty("os.name") + " "
                + System.getProperty("os.version"));

        Logger.logWarn("Test color warning");
        Logger.logError("Test color error");

    }

    /**
     * Verifies command line arguments to check.
     *
     * @param args Command line arguments to be verified.
     * @return true if arguments are correct; otherwise, false.
     */
    private static boolean verifyArguments(String[] args) {
        return args.length == 1;
    }
}
