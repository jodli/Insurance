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
package src.jodli.Client.Utilities.DatabaseModels;

import src.jodli.Client.Utilities.ESetting;
import src.jodli.Client.Utilities.SettingsUtils;

/**
 * Model for the settings database.
 *
 * @author Jan-Olaf Becker
 */
public class ModelSettings {

    // settings database -> key - value
    private int m_Key;
    private Object m_Value;
    private Object m_DefaultValue;

    /**
     * Constructor to create empty settings.
     *
     * @see SettingsUtils
     */
    public ModelSettings(ESetting s) {
//        m_Key = s.getKey();
//        m_DefaultValue = s.getDefaultValue();
        m_Value = null;
    }

    /**
     * Constructor to create new settings for saving in database.
     *
     * @param s   Ordinal of setting according to enum.
     * @param val Value of setting.
     * @see SettingsUtils
     */
    public ModelSettings(ESetting s, Object val) {
        this(s);
        m_Value = val;
    }

    /**
     * @return Get mapping key.
     */
    public String getKey() {
        return Integer.toString(m_Key);
    }

    /**
     * @return Get value for current key.
     */
    public Object getValue() {
        return m_Value;
    }

    /**
     * @param value Set value for current key.
     */
    public void setValue(Object value) {
        m_Value = value;
    }

    /**
     * @return Get default value for current key.
     */
    public Object getDefaultValue() {
        return m_DefaultValue;
    }

    /**
     * @param defValue Set default value for current key.
     */
    public void setDefaultValue(Object defValue) {
        m_DefaultValue = defValue;
    }
}
