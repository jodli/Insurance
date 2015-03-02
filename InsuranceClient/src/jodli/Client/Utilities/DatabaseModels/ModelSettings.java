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

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import src.jodli.Client.Utilities.Setting;
import src.jodli.Client.Utilities.SettingsUtils;

/**
 * Model for the settings database.
 *
 * @author Jan-Olaf Becker
 */
@DatabaseTable(tableName = "Settings")
public class ModelSettings {

    // settings database -> key - value
    @DatabaseField(id = true)
    private int key;
    @DatabaseField
    private String value;

    /**
     * Constructor to create empty settings.
     *
     * @see SettingsUtils
     */
    public ModelSettings() {
        this.key = -1;
        this.value = "";
    }

    /**
     * Constructor to create new settings for saving in database.
     *
     * @param key Ordinal of setting according to enum.
     * @param val Value of setting.
     * @see SettingsUtils
     */
    public ModelSettings(Setting key, String val) {
        this.key = key.getKey();
        this.value = val;
    }

    /**
     * @return Get mapping key.
     */
    public Setting getKey() {
        return Setting.fromKey(this.key);
    }

    /**
     * @param key Set mapping key.
     */
    public void setKey(int key) {
        this.key = key;
    }

    /**
     * @return Get value for current key.
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value Set value for current key.
     */
    public void setValue(String value) {
        this.value = value;
    }
}
