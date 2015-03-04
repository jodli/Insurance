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

import src.jodli.Client.log.Logger;

import java.util.Objects;
import java.util.prefs.Preferences;

/**
 * Access the settings stored in database.
 *
 * @author Jan-Olaf Becker
 */
public class SettingsUtils {

    private final static String SETTINGS_NODE = "jodli/Settings";
    private static SettingsUtils m_Instance = null;
    private Preferences m_Preferences = Preferences.userRoot().node(SETTINGS_NODE);

    private SettingsUtils() {
    }

    public static String getValue(ESetting s) {
        Logger.logDebug("Getting value of " + s.getKey());
        return getInstance().m_Preferences.get(s.getKey(), s.getDefaultValue());
    }

    public static boolean setValue(ESetting s, String value) {
        if (Objects.equals(getValue(s), value)) {
            Logger.logDebug("Value not changed - Not saving.");
            return false;
        }
        Logger.logDebug("Value changed - Saving.");
        getInstance().m_Preferences.put(s.getKey(), value);
        Logger.logDebug("Setting value of " + s.getKey() + " to " + value);
        return true;
    }

    public static SettingsUtils getInstance() {
        if (m_Instance == null) {
            m_Instance = new SettingsUtils();
        }
        return m_Instance;
    }
}
