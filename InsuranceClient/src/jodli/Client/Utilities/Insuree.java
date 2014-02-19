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
 * Enum to store column index.
 *
 * @author Jan-Olaf Becker
 *
 */
public enum Insuree {

    /**
     * Buildnumber value mapped to key = 0.
     */
    Default (0),
    PreName (1);

    private final int Key;

    private Insuree ( int key ) {
        this.Key = key;
    }

    public int getKey () {
        return this.Key;
    }

    @Override
    public String toString () {
        String s = super.toString ();
        return s.substring (0, 1) + s.substring (1).toLowerCase ();
    }

    /**
     * Gets enum from a given key.
     *
     * @param key Integer corresponding to enum.
     *
     * @return Insuree enum.
     *
     * @throws IllegalArgumentException
     */
    public static Insuree fromKey ( int key ) throws IllegalArgumentException {
        try {
            for (Insuree insuree : values ()) {
                if (insuree.getKey () == key) {
                    return insuree;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            Logger.logError (e.getMessage (), e);
        }
        return Insuree.Default;
    }
}
