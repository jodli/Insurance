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

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import src.jodli.Client.log.Logger;

import java.io.File;
import java.sql.SQLException;
import java.util.Observable;

/**
 * Database utilities. Creates connection and helper classes.
 *
 * @author Jan-Olaf Becker
 */
public class DatabaseUtils extends Observable {

    private static DatabaseUtils m_Instance = null;
    private final String DATABASEURL = "jdbc:sqlite:";
    private String m_DatabasePath;
    private ConnectionSource m_Connection = null;

    private DatabaseUtils() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e1) {
            Logger.logError(e1.getMessage(), e1);
        }
    }

    public static void openDatabase() {
        closeDatabase();
        File filePath = new File(SettingsUtils.getValue(ESetting.LASTDATABASE));
        m_Instance.m_DatabasePath = filePath.getAbsolutePath();
        m_Instance.openConnection();

        m_Instance.setChanged();
        m_Instance.notifyObservers(m_Instance.m_Connection);
    }

    public static void closeDatabase() {
        if (m_Instance.m_Connection != null) {
            m_Instance.closeConnection();
        }
    }

    /**
     * Initializes singleton.
     */
    public static DatabaseUtils getInstance() {
        if (m_Instance == null) {
            m_Instance = new DatabaseUtils();
        }
        return m_Instance;
    }

    private void openConnection() {
        Logger.logInfo("Opening database: " + m_DatabasePath);
        try {
            m_Connection = new JdbcConnectionSource(DATABASEURL + m_DatabasePath);

            // create database utilities
        } catch (SQLException e) {
            Logger.logError(e.getMessage(), e);
        }
    }

    private void closeConnection() {
        try {
            if (m_Connection.isOpen()) {
                m_Connection.close();
            }
        } catch (SQLException e) {
            Logger.logError(e.getMessage(), e);
        }
    }
}
