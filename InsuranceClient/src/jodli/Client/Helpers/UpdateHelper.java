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
package jodli.Client.Helpers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class UpdateHelper {

	private final static String changeLogURL = "file:///"
			+ System.getProperty("user.dir") + File.separator + "res"
			+ File.separator + "changelog.txt";
	private final static String versionURL = "file:///"
			+ System.getProperty("user.dir") + File.separator + "res"
			+ File.separator + "version.txt";

	public static String getLatestVersion() {
		String data = getData(versionURL);
		return data.substring(data.indexOf("[version]") + 9,
				data.indexOf("[/version]"));
	}

	public static String getChangeLog() {
		String data = getData(changeLogURL);
		return data.substring(data.indexOf("[changelog]") + 11,
				data.indexOf("[/changelog]"));
	}

	private static String getData(String uri) {
		StringBuffer buffer = null;
		try {
			URL url = new URL(uri);

			InputStream html = null;

			html = url.openStream();

			int c = 0;
			buffer = new StringBuffer("");

			while (c != -1) {
				c = html.read();

				buffer.append((char) c);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}
}
