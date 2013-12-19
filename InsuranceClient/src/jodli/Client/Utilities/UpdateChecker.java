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
package jodli.Client.Utilities;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;

import jodli.Client.Application.App;
import jodli.Client.Updater.SelfUpdate;
import jodli.Client.Updater.UpdateInfo;
import jodli.Client.log.Logger;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class UpdateChecker {

	private int version;
	private int latest;
	public static String verString = "";
	private String downloadAddress = "";
	private String changeLog = "";

	private final static String updateFileURL = "\\\\home.audivo.local\\_users\\beckerjo\\updateFile.xml";

	public UpdateChecker(int version) {
		this.version = version;
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
			File url = new File(updateFileURL);
			Document doc = AppUtils.downloadXML(url.toURI().toURL());
			if (doc == null) {
				return;
			}
			Element updateInfoNode = (Element) doc.getElementsByTagName(
					"updateinfo").item(0);

			String ver = updateInfoNode.getAttribute("currentBuild");
			this.latest = Integer.parseInt(ver);

			char[] tmp = ver.toCharArray();
			for (int i = 0; i < (tmp.length - 1); i++) {
				verString += tmp[i] + ".";
			}
			verString += tmp[tmp.length - 1];
			Logger.logInfo("Current build: " + verString);

			downloadAddress = updateInfoNode.getAttribute("downloadURL");

			changeLog = doc.getElementsByTagName("changelog").item(0)
					.getTextContent();

		} catch (Exception e) {
			Logger.logError(e.getMessage(), e);
		}
	}

	public boolean shouldUpdate() {
		return version < latest;
	}

	public void update() {
		UpdateInfo ui = new UpdateInfo(changeLog, downloadAddress);
	}
}
