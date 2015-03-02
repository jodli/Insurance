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

import src.jodli.Client.log.Logger;

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
    BUILDNUMBER(0),
    CHECKUPDATE(1);

    public static final String DEFAULT_BUILDNUMBER = "00";
    public static final String DEFAULT_CHECKUPDATE = "true";

    private int Key;

    private Setting(int key) {
        this.Key = key;
    }

    /**
     * Gets enum from a give key.
     *
     * @param key Integer corresponding to enum.
     * @return Setting enum.
     * @throws IllegalArgumentException
     */
    public static Setting fromKey(int key) throws IllegalArgumentException {
        try {
            return Setting.values()[key];
        } catch (ArrayIndexOutOfBoundsException e) {
            Logger.logError(e.getMessage(), e);
        }
        return null;
    }

    public int getKey() {
        return this.Key;
    }

    @Override
    public String toString() {
        String s = super.toString();
        return s.substring(0, 1) + s.substring(1).toLowerCase();
    }

    ;
}
