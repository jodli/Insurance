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
import jodli.Client.log.Logger;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;

public class UpdateChecker {

	private int version;
	private int latest;
	public static String verString = "";
	private String downloadAddress = "";

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
			NamedNodeMap updateAttributes = doc.getDocumentElement()
					.getAttributes();
			this.latest = Integer.parseInt(updateAttributes.getNamedItem(
					"currentBuild").getTextContent());
			char[] tmp = String.valueOf(latest).toCharArray();
			for (int i = 0; i < (tmp.length - 1); i++) {
				verString += tmp[i] + ".";
			}
			verString += tmp[tmp.length - 1];
			downloadAddress = updateAttributes.getNamedItem("downloadURL")
					.getTextContent();
		} catch (Exception e) {
			Logger.logError(e.getMessage(), e);
		}
	}

	public boolean shouldUpdate() {
		return version < latest;
	}

	public void update() {
		String path = null;
		try {
			path = new File(App.class.getProtectionDomain().getCodeSource()
					.getLocation().getPath()).getCanonicalPath();
			path = URLDecoder.decode(path, "UTF-8");
		} catch (IOException e) {
			Logger.logError("Couldn't get path to current Application.", e);
		}

		String temporaryUpdatePath = OSUtils.getDynamicStorageLocation()
				+ "updatetemp" + File.separator
				+ path.substring(path.lastIndexOf(File.separator) + 1);

		try {
			File temporaryUpdate = new File(temporaryUpdatePath);
			temporaryUpdate.getParentFile().mkdir();
			AppUtils.downloadToFile(new URL(downloadAddress), temporaryUpdate);
			SelfUpdate.runUpdate(path, temporaryUpdatePath);
		} catch (Exception e) {
			Logger.logError(e.getMessage(), e);
		}
	}
}
