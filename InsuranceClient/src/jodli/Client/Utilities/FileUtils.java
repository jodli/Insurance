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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class FileUtils {
	public static boolean delete(File resource) throws IOException {
		if (resource.isDirectory()) {
			File[] childFiles = resource.listFiles();
			for (File child : childFiles) {
				delete(child);
			}
		}
		return resource.delete();
	}

	public static void copyFile(File sourceFile, File destinationFile)
			throws IOException {
		copyFile(sourceFile, destinationFile, true);
	}

	public static void copyFile(File sourceFile, File destinationFile,
			boolean overwrite) throws IOException {
		if (sourceFile.exists()) {
			if (!destinationFile.exists()) {
				destinationFile.getParentFile().mkdirs();
				destinationFile.createNewFile();
			} else if (!overwrite)
				return;
			FileChannel sourceStream = null, destinationStream = null;
			try {
				sourceStream = new FileInputStream(sourceFile).getChannel();
				destinationStream = new FileOutputStream(destinationFile)
						.getChannel();
				destinationStream.transferFrom(sourceStream, 0,
						sourceStream.size());
			} finally {
				if (sourceStream != null) {
					sourceStream.close();
				}
				if (destinationStream != null) {
					destinationStream.close();
				}
			}
		}
	}
}
