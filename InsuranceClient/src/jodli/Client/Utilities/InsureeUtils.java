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

import com.j256.ormlite.dao.BaseDaoImpl;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import src.jodli.Client.Utilities.DatabaseModels.ModelInsuree;
import src.jodli.Client.log.Logger;

import com.j256.ormlite.dao.Dao.CreateOrUpdateStatus;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableInfo;
import com.j256.ormlite.table.TableUtils;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

/**
 * Access the insurees stored in database.
 *
 * @author Jan-Olaf Becker
 *
 */
public class InsureeUtils {

    private static BaseDaoImpl<ModelInsuree, Integer> insureeDao = null;
    private static TableInfo<ModelInsuree, Integer> tableInfo = null;

    /**
     * Constructs new InsureeUtils. Also creates table corresponding to
     * ModelInsuree if it doesn't exist and Dao.
     *
     * @param conn Connection variable. Should come from DatabaseUtils.
     *
     * @see DatabaseUtils
     * @see ModelInsuree
     */
    public InsureeUtils ( ConnectionSource conn ) {
        try {
            TableUtils.createTableIfNotExists (conn, ModelInsuree.class);
            insureeDao = DaoManager.createDao (conn, ModelInsuree.class);

            tableInfo = new TableInfo<ModelInsuree, Integer> (conn, insureeDao, ModelInsuree.class);
        } catch (SQLException e) {
            Logger.logError (e.getMessage (), e);
        }
    }

    /**
     * Gets ModelInsuree corresponding to id from insuree in database.
     *
     * @param id ID as key.
     *
     * @return ModelInsuree corresponding to ID.
     *
     * @see ModelInsuree
     */
    public static ModelInsuree getValue ( int id ) {
        ModelInsuree m = null;
        try {
            m = insureeDao.queryForId (id);
        } catch (SQLException e) {
            Logger.logError (e.getMessage (), e);
        }
        return m;
    }

    /**
     * Inserts or updates ModelInsuree corresponding to key in insuree database.
     *
     * @param m New ModelInsuree object to be inserted or updated.
     *
     * @return true if the operation was successful; otherwise, false.
     *
     * @see ModelInsuree
     */
    public static boolean setValue ( ModelInsuree m ) {
        try {
            CreateOrUpdateStatus status = insureeDao.createOrUpdate (m);
            if (status.isCreated () || status.isUpdated ()) {
                return true;
            }
        } catch (SQLException e) {
            Logger.logError (e.getMessage (), e);
        }
        return false;
    }

    /**
     * Gets a list containing all ModelInsurees in the database.
     *
     * @return List of ModelInsurees.
     *
     * @see ModelInsuree
     */
    public static List<ModelInsuree> getAll () {
        List<ModelInsuree> list = new ArrayList<ModelInsuree> ();
        try {
            list = insureeDao.queryForAll ();
        } catch (SQLException e) {
            Logger.logError (e.getMessage (), e);
        }
        return list;
    }

    public static DefaultTableModel getModel () {
        Vector<String> names = new Vector<String> ();
        for (FieldType field : tableInfo.getFieldTypes ()) {
            names.add (field.getColumnName ());
            Logger.logInfo (field.getColumnName ());
        }

        return new DefaultTableModel (names, 1);
    }
}
