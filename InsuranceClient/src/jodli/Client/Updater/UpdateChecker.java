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

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import src.jodli.Client.Updater.UpdateInfo;
import src.jodli.Client.Utilities.AppUtils;
import src.jodli.Client.Utilities.FileUtils;
import src.jodli.Client.Utilities.OSUtils;
import src.jodli.Client.log.Logger;

import java.io.File;
import java.net.URL;

public class UpdateChecker {

    private final static String updateFileURL = "https://raw.github.com/jodli/Insurance/master/InsuranceClient/res/updateFile.xml";
    private int buildNumber;
    private int latestBuild;
    private String buildString;
    private String downloadAddress = "";
    private String changeLog = "";

    public UpdateChecker(String buildNumber) {
        this.buildNumber = Integer.parseInt(buildNumber);
        loadInfo();
        try {
            FileUtils.delete(new File(OSUtils.getDynamicStorageLocation(),
                    "updatetemp"));
        } catch (Exception e) {
            Logger.logError(e.getMessage(), e);
        }
    }

    private void loadInfo() {
        try {
            Document doc = AppUtils.downloadXML(new URL(updateFileURL));
            if (doc == null) {
                return;
            }
            Element updateInfoNode = (Element) doc.getElementsByTagName(
                    "updateinfo").item(0);

            buildString = updateInfoNode.getAttribute("currentBuild");
            this.latestBuild = Integer.parseInt(buildString);

            Logger.logInfo("Current build: " + AppUtils.getVerboseVersion(buildString));

            downloadAddress = updateInfoNode.getAttribute("downloadURL");

            changeLog = doc.getElementsByTagName("changelog").item(0)
                    .getTextContent();

        } catch (Exception e) {
            Logger.logError(e.getMessage(), e);
        }
    }

    public boolean shouldUpdate() {
        return buildNumber < latestBuild;
    }

    public void update() {
        UpdateInfo ui = new UpdateInfo(changeLog, downloadAddress, buildString);
        ui.showFrame();
    }
}
