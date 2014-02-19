/*
 * Copyright (C) 2014 Jan-Olaf Becker <job87@web.de>
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
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package src.jodli.Client.Utilities.DatabaseModels;

import com.j256.ormlite.support.DatabaseResults;
import java.sql.SQLException;
import javax.swing.table.AbstractTableModel;
import src.jodli.Client.Utilities.Insuree;
import src.jodli.Client.Utilities.InsureeUtils;
import src.jodli.Client.log.Logger;

/**
 *
 * @author Jan-Olaf Becker <job87@web.de>
 */
public class InsureeTableModel extends AbstractTableModel {

    public InsureeTableModel () {
    }

    public int getRowCount () {
        return InsureeUtils.getRowCount ();
    }

    public int getColumnCount () {
        return InsureeUtils.getColumnCount ();
    }

    public Object getValueAt ( int row, int column ) {
        try {
            DatabaseResults dr = InsureeUtils.getResultSet ();

            for (int i = 0; i < row; i++) {
                if (!dr.next ()) {
                    return "";
                }
            }
            return dr.getString (column);
        } catch (SQLException ex) {
            Logger.logError (ex.getMessage (), ex);
        }
        return "";
    }

    @Override
    public String getColumnName ( int i ) {
        Insuree ins = Insuree.fromKey (i);
        if (ins != null) {
            return ins.toString ();
        }
        return "";
    }
}
