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
import src.jodli.Client.Utilities.EInsurance;
import src.jodli.Client.Utilities.InsuranceUtils;
import src.jodli.Client.log.Logger;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by job87 on 3/4/2015.
 */
public final class InsuranceTableModel extends TableModel {

    private SimpleDateFormat dateFormatter;
    private int m_InsureeID;

    /**
     * Creates a new InsuranceTableModel object.
     */
    public InsuranceTableModel() {
        dateFormatter = new SimpleDateFormat("dd.MM.yyyy");
        update(-1);
    }

    public void update(int insureeID) {
        Logger.logDebug("Updating Insurance Table.");
        if (insureeID < 0 || m_InsureeID != insureeID) {
            new InsuranceTableTask(insureeID).execute();
            m_InsureeID = insureeID;
        }
    }

    public Object getValueAt(int row, int column) {
        ModelInsurance m = (ModelInsurance) this.rows.get(row);
        return getColumnValue(m, column);
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        // make a copy of the insurance MEMENTO
        ModelInsurance insurance = new ModelInsurance((ModelInsurance) this.rows.get(rowIndex));
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
            case ModelInsurance.FIELD_ID:
                ret = row.getID();
                break;
            case ModelInsurance.FIELD_INSURANCENO:
                ret = row.getInsuranceNo();
                break;
            case ModelInsurance.FIELD_START:
                ret = row.getStart() != null ? dateFormatter.format(row.getStart()) : null;
                break;
            case ModelInsurance.FIELD_CONTRACT_DATE:
                ret = row.getContractDate() != null ? dateFormatter.format(row.getContractDate()) : null;
                break;
            case ModelInsurance.FIELD_END:
                ret = row.getEnd() != null ? dateFormatter.format(row.getEnd()) : null;
                break;
            case ModelInsurance.FIELD_TYPE:
                ret = row.getType().toString();
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
            case ModelInsurance.FIELD_INSURANCENO:
                row.setInsuranceNo(val.toString());
                break;
            case ModelInsurance.FIELD_START:
                row.setStart((Date) val);
                break;
            case ModelInsurance.FIELD_CONTRACT_DATE:
                row.setContractDate((Date) val);
                break;
            case ModelInsurance.FIELD_END:
                row.setEnd((Date) val);
                break;
            case ModelInsurance.FIELD_TYPE:
                row.setType(EInsurance.valueOf((String) val));
            default:
                return false;
        }
        return true;
    }

    private final class InsuranceTableTask extends SwingWorker<Void, ModelInsurance> {

        private int m_InsureeID;

        public InsuranceTableTask(int insureeID) {
            m_InsureeID = insureeID;
            rows.clear();
        }

        @Override
        protected Void doInBackground() throws Exception {
            CloseableIterator<ModelInsurance> res = InsuranceUtils.getInstance().getResultSet(m_InsureeID);

            if (columns == null) {
                List<String> columnList = new LinkedList<>(Arrays.asList(res.getRawResults().getColumnNames()));
                columnList.remove(0);
                columnList.remove(0);
                columns = columnList.toArray(new String[0]);
                InsuranceTableModel.this.fireTableStructureChanged();
            }

            Logger.logDebug("Insurance Columns: " + InsuranceTableModel.this.getColumnCount());

            boolean hasEntry = false;
            while (res.hasNext()) {
                publish(res.next());
                hasEntry = true;
            }
            if (!hasEntry) {
                InsuranceTableModel.this.fireTableStructureChanged();
            }
            return null;
        }

        @Override
        protected void process(List<ModelInsurance> chunks) {
            Logger.logDebug("Processing " + chunks.size() + " chunks.");
            rows.addAll(chunks);
            InsuranceTableModel.this.fireTableDataChanged();
        }
    }
}
