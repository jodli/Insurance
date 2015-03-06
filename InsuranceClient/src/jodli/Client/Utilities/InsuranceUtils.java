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

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import src.jodli.Client.Utilities.DatabaseModels.ModelInsurance;
import src.jodli.Client.log.Logger;

import java.sql.SQLException;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by job87 on 3/5/2015.
 */
public class InsuranceUtils implements Observer {

    private static BaseDaoImpl<ModelInsurance, Integer> m_InsuranceDao = null;
    private static InsuranceUtils m_Instance = null;

    private InsuranceUtils() {

    }

    public static InsuranceUtils getInstance() {
        if (m_Instance == null) {
            m_Instance = new InsuranceUtils();
        }
        return m_Instance;
    }

    private void connect(ConnectionSource conn) {
        Logger.logDebug("Connecting to Insurance Table.");
        try {
            TableUtils.createTableIfNotExists(conn, ModelInsurance.class);
            m_InsuranceDao = DaoManager.createDao(conn, ModelInsurance.class);

        } catch (SQLException e) {
            Logger.logError(e.getMessage(), e);
        }
    }

    public int getRowCount() {
        try {
            return (int) m_InsuranceDao.countOf();
        } catch (SQLException ex) {
            Logger.logError(ex.getMessage(), ex);
            return -1;
        }
    }

    public CloseableIterator<ModelInsurance> getResultSet(int insureeID) {
        CloseableIterator<ModelInsurance> it = null;
        try {
            if (insureeID < 0) {
                it = m_InsuranceDao.iterator();
            } else {
                PreparedQuery p = m_InsuranceDao.queryBuilder().where().eq(ModelInsurance.FIELD_ID, insureeID).prepare();
                it = m_InsuranceDao.iterator(p);
            }
        } catch (SQLException e) {
            Logger.logError(e.getMessage(), e);
        }
        return it;
    }

    public boolean setValue(ModelInsurance m) {
        try {
            Dao.CreateOrUpdateStatus status = m_InsuranceDao.createOrUpdate(m);
            if (status.isCreated() || status.isUpdated()) {
                return true;
            }
        } catch (SQLException e) {
            Logger.logError(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o == DatabaseUtils.getInstance()) {
            connect(((ConnectionSource) arg));
        }
    }
}
