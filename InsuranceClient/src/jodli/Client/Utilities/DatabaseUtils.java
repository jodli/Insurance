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
import java.sql.SQLException;
import java.util.Calendar;

import src.jodli.Client.log.Logger;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

/**
 * Database utilities. Creates connection and helper classes.
 *
 * @author Jan-Olaf Becker
 *
 */
public class DatabaseUtils {

    private static final String DB_PATH = System.getProperty ("user.dir")
                                          + File.separator + "database_"
                                          + Calendar.getInstance ().get (Calendar.YEAR) + ".sqlite";

    private final String databaseUrl = "jdbc:sqlite:" + DB_PATH;
    private ConnectionSource conn = null;

    private static DatabaseUtils instance = null;

    private DatabaseUtils () {
        try {
            Class.forName ("org.sqlite.JDBC");
        } catch (ClassNotFoundException e1) {
            Logger.logError (e1.getMessage (), e1);
        }

        Logger.logInfo ("Opening database at path: " + DB_PATH);
        try {
            conn = new JdbcConnectionSource (databaseUrl);

            // create database utilities
            new SettingsUtils (conn);
            new InsureeUtils (conn);
        } catch (SQLException e) {
            Logger.logError (e.getMessage (), e);
        }
    }

    /**
     * Initializes singleton.
     */
    public static void init () {
        if (instance == null) {
            instance = new DatabaseUtils ();
        }
    }
}
