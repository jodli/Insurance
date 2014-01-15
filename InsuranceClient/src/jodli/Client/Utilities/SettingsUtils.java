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

import java.sql.SQLException;
import java.util.List;

import src.jodli.Client.log.Logger;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * Access the settings stored in database.
 * 
 * @author Jan-Olaf Becker
 * 
 */
public class SettingsUtils {

	private static Dao<ModelSettings, Integer> settingsDao = null;

	private static List<ModelSettings> listSettings = null;

	/**
	 * Constructs new SettingsUtils. Also creates table corresponding to
	 * ModelSettings if it doesn't exist. Creates Dao and calls loadSettings.
	 * 
	 * @param conn
	 *            Connection variable. Should come from DatabaseUtils.
	 * @see DatabaseUtils
	 * @see ModelSettings
	 */
	public SettingsUtils(ConnectionSource conn) {
		try {
			TableUtils.createTableIfNotExists(conn, ModelSettings.class);
			settingsDao = DaoManager.createDao(conn, ModelSettings.class);

			loadSettings();
		} catch (SQLException e) {
			Logger.logError(e.getMessage(), e);
		}
	}

	/**
	 * Load settings from database into list.
	 * 
	 * @return Number of items in list.
	 * @throws SQLException
	 */
	public static int loadSettings() throws SQLException {
		listSettings = settingsDao.queryForAll();
		return listSettings.size();
	}

	/**
	 * Gets value corresponding to key from settings in database.
	 * 
	 * @param s
	 *            Enum value as key.
	 * @return Value corresponding to s.
	 * @see Setting
	 */
	public static String getValue(Setting s) {
		for (ModelSettings set : listSettings) {
			if (set.getKey() == s) {
				return set.getValue();
			}
		}
		return null;
	}

	/**
	 * Inserts or updates value corresponding to key in settings database.
	 * 
	 * @param m
	 *            New ModelSettings object to be inserted or updated.
	 * @return true if the operation was successful; otherwise, false.
	 */
	public static boolean setValue(ModelSettings m) {
		try {
			settingsDao.createOrUpdate(m);
			loadSettings();
			return true;
		} catch (SQLException e) {
			Logger.logError(e.getMessage(), e);
		}
		return false;
	}
}
