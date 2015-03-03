/*
 *
 *  * Copyright (C) 2013 Jan-Olaf Becker
 *  *
 *  * This program is free software; you can redistribute it and/or
 *  * modify it under the terms of the GNU General Public License
 *  * as published by the Free Software Foundation; either version 2
 *  * of the License, or (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program; if not, write to the Free Software
 *  * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 */
package src.jodli.Client.Utilities;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.Dao.CreateOrUpdateStatus;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import src.jodli.Client.Utilities.DatabaseModels.ModelSettings;
import src.jodli.Client.log.Logger;

import java.sql.SQLException;

/**
 * Access the settings stored in database.
 *
 * @author Jan-Olaf Becker
 */
public class SettingsUtils {

    private static Dao<ModelSettings, Integer> settingsDao = null;

    /**
     * Constructs new SettingsUtils. Also creates table corresponding to
     * ModelSettings if it doesn't exist and Dao.
     *
     * @param conn Connection variable. Should come from DatabaseUtils.
     * @see DatabaseUtils
     * @see ModelSettings
     */
    public SettingsUtils(ConnectionSource conn) {
        try {
            TableUtils.createTableIfNotExists(conn, ModelSettings.class);
            settingsDao = DaoManager.createDao(conn, ModelSettings.class);
        } catch (SQLException e) {
            Logger.logError(e.getMessage(), e);
        }
    }

    /**
     * Gets value corresponding to key from settings in database.
     * Creates entry in database if it does not exist.
     *
     * @param s Enum value as key.
     * @return Value corresponding to s.
     * @see Setting
     */
    public static String getValue(Setting s) {
        ModelSettings m;
        try {
            m = settingsDao.queryForId(s.getKey());
            if (m != null) {
                return m.getValue();
            }
        } catch (SQLException e) {
            Logger.logError(e.getMessage(), e);
        }
        createSetting(s);
        return getValue(s);
    }

    /**
     * Inserts or updates value corresponding to key in settings database.
     *
     * @param m New ModelSettings object to be inserted or updated.
     * @return true if the operation was successful; otherwise, false.
     */
    public static boolean setValue(ModelSettings m) {
        try {
            CreateOrUpdateStatus status = settingsDao.createOrUpdate(m);
            if (status.isCreated() || status.isUpdated()) {
                return true;
            }
        } catch (SQLException e) {
            Logger.logError(e.getMessage(), e);
        }
        return false;
    }

    private static boolean createSetting(Setting s) {
        ModelSettings m = new ModelSettings();
        m.setKey(s.getKey());

        switch (s) {
            case BUILDNUMBER:
                m.setValue(s.DEFAULT_BUILDNUMBER);
                break;
            case CHECKUPDATE:
                m.setValue(s.DEFAULT_CHECKUPDATE);
                break;
            case LOGTYPE:
                m.setValue(s.DEFAULT_LOGTYPE);
                break;
            default:
                throw new IllegalArgumentException("No default Setting found.");
        }
        return setValue(m);
    }
}
