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

/**
 * Enum to store settings keys mapped to database key.
 *
 * @author Jan-Olaf Becker
 */
public enum Setting {
    // add more settings here as we go...
    // constructor takes the key!
    /**
     * Buildnumber value mapped to key = 0.
     * CheckUpdate value mapped to key = 1.
     */
    BUILDNUMBER("BuildNumber", "00"),
    CHECKUPDATE("CheckUpdate", "true"),
    LOGTYPE("LogLype", "MINIMAL");

    private String m_Key;
    private String m_DefaultValue;

    private Setting(String key, String defaultValue) {
        m_Key = key;
        m_DefaultValue = defaultValue;
    }

    public String getKey() {
        return m_Key;
    }

    public String getDefaultValue() {
        return m_DefaultValue;
    }

    @Override
    public String toString() {
        String s = super.toString();
        return s.substring(0, 1) + s.substring(1).toLowerCase();
    }
}
