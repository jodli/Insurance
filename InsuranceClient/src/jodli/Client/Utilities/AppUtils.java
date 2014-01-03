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
package src.jodli.Client.Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class AppUtils {

	public static String downloadString(URL url) throws IOException {
		return readString(url.openStream());
	}

	public static String readString(InputStream stream) {
		Scanner scanner = new Scanner(stream).useDelimiter("\\A");
		return scanner.hasNext() ? scanner.next() : "";
	}

	public static Document downloadXML(URL url) throws IOException,
			SAXException {
		return getXML(url.openStream());
	}

	public static Document readXML(File file) throws IOException, SAXException {
		return getXML(new FileInputStream(file));
	}

	public static Document getXML(InputStream stream) throws IOException,
			SAXException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory
				.newInstance();
		try {
			return docFactory.newDocumentBuilder().parse(stream);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void downloadToFile(URL url, File file) throws IOException {
		file.getParentFile().mkdirs();
		ReadableByteChannel rbc = Channels.newChannel(url.openStream());
		FileOutputStream fos = new FileOutputStream(file);
		fos.getChannel().transferFrom(rbc, 0, 1 << 24);
		fos.close();
	}

	public static String getVersion(String buildNumber) {
		String version = "";

		char[] tmp = buildNumber.toCharArray();
		for (int i = 0; i < (tmp.length - 1); i++) {
			version += tmp[i] + ".";
		}
		version += tmp[tmp.length - 1];
		return version;
	}
}
