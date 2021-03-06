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

public class OSUtils {
	private static String cachedUserHome;

	public static enum OS {
		WINDOWS, UNIX, OTHER
	}

	static {
		cachedUserHome = System.getProperty("user.home");
	}

	public static String getDynamicStorageLocation() {
		switch (getCurrentOS()) {
		case WINDOWS:
			return System.getProperty("user.dir") + File.separator;
		case UNIX:
			return cachedUserHome + "/.insurance/";
		default:
			return null;
		}
	}

	public static OS getCurrentOS() {
		String osString = System.getProperty("os.name").toLowerCase();
		if (osString.contains("win")) {
			return OS.WINDOWS;
		} else if (osString.contains("nix") || osString.contains("nux")) {
			return OS.UNIX;
		} else {
			return OS.OTHER;
		}
	}
}
