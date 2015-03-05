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

package src.jodli.Client.TableModels;

import com.j256.ormlite.dao.CloseableIterator;
import src.jodli.Client.Utilities.DatabaseModels.ModelInsurance;
import src.jodli.Client.Utilities.InsuranceUtils;
import src.jodli.Client.log.Logger;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by job87 on 3/4/2015.
 */
public final class InsuranceTableModel extends TableModel<ModelInsurance> {

    private SimpleDateFormat dateFormatter;
    private SwingWorker<Void, ModelInsurance> worker;
    private int m_InsureeID;

    /**
     * Creates a new InsureeTableModel object. Starts the SwingWorker to process
     * the rows and columns in the database.
     */
    public InsuranceTableModel() {
        dateFormatter = new SimpleDateFormat("dd.MM.yyyy");
        m_InsureeID = -1;
        worker = new SwingWorker<Void, ModelInsurance>() {

            @Override
            protected Void doInBackground() throws Exception {
                CloseableIterator<ModelInsurance> res = InsuranceUtils.getInstance().getResultSet(m_InsureeID);

                columns = res.getRawResults().getColumnNames();
                InsuranceTableModel.this.fireTableStructureChanged();
                Logger.logDebug("Insurance Columns: " + InsuranceTableModel.this.getColumnCount());

                while (res.hasNext()) {
                    publish(res.next());
                }
                return null;
            }

            @Override
            protected void process(List<ModelInsurance> chunks
            ) {
                rows.addAll(chunks);
                InsuranceTableModel.this.fireTableDataChanged();
                Logger.logDebug("Processing " + chunks.size() + " chunks.");
            }
        };
    }

    public void update(int insureeID) {
        Logger.logDebug("Updating Insurance Table.");
        m_InsureeID = insureeID;
        worker.execute();
    }

    public int getId(int row) {
        return this.rows.get(row).getID();
    }

    @Override
    public Object getValueAt(int row, int column) {
        return getColumnValue(this.rows.get(row), column);
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        // make a copy of the insuree MEMENTO
        ModelInsurance insurance = new ModelInsurance(this.rows.get(rowIndex));
        // set column value in copy
        if (this.setColumnValue(insurance, columnIndex, aValue)) {
            // update database
            if (InsuranceUtils.getInstance().setValue(insurance)) {
                // if updated successful copy in rows list and fire event.
                this.rows.set(rowIndex, insurance);
                this.fireTableCellUpdated(rowIndex, columnIndex);
                Logger.logDebug("Row: " + rowIndex + " Column: " + columnIndex + " Value: " + aValue.toString() + " Updated successfully!");
                return;
            }
        }
        // if database update failed revert state of insuree (NOP)
        Logger.logError("Row: " + rowIndex + " Column: " + columnIndex + " Value: " + aValue.toString() + "Error while updating; Reverting!");
    }

    /**
     * Gets the value of the column of the specified row.
     *
     * @param row    The ModelInsurance object that is saved in the database.
     * @param column The column where the value is saved in.
     */
    private Object getColumnValue(ModelInsurance row, int column) {
        Object ret = null;
        switch (this.columns[column]) {
            case "ID":
                ret = row.getID();
                break;
            case "Versicherungsnummer":
                ret = row.getInsuranceNo();
                break;
            default:
                ret = "ERROR";
                break;
        }
        return ret;
    }

    /**
     * @param row    The ModelInsurance object that is saved in the database.
     * @param column The column where the value is saved in.
     * @param val    The value that is saved in the cell.
     * @return true if the value was set; otherwise, false.
     */
    private boolean setColumnValue(ModelInsurance row, int column, Object val) {
        switch (this.columns[column]) {
            case "Versicherungsnummer":
                row.setInsuranceNo(val.toString());
                break;
            default:
                return false;
        }
        return true;
    }
}
