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
package src.jodli.Client.TableModels;

import com.j256.ormlite.dao.CloseableIterator;
import src.jodli.Client.Utilities.DatabaseModels.ModelInsuree;
import src.jodli.Client.Utilities.InsureeUtils;
import src.jodli.Client.log.Logger;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * TableModel to be used to display the Insuree table in the database.
 *
 * @author Jan-Olaf Becker <job87@web.de>
 */
public final class InsureeTableModel extends TableModel<ModelInsuree> {

    private SimpleDateFormat dateFormatter;

    /**
     * Creates a new InsureeTableModel object. Starts the SwingWorker to process
     * the rows and columns in the database.
     */
    public InsureeTableModel() {
        dateFormatter = new SimpleDateFormat("dd.MM.yyyy");
        update();
    }

    public void update() {
        Logger.logDebug("Updating Insuree Table.");
        new InsureeTableTask().execute();
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
        ModelInsuree insuree = new ModelInsuree(this.rows.get(rowIndex));
        // set column value in copy
        if (this.setColumnValue(insuree, columnIndex, aValue)) {
            // update database
            if (InsureeUtils.getInstance().setValue(insuree)) {
                // if updated successful copy in rows list and fire event.
                this.rows.set(rowIndex, insuree);
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
     * @param row    The ModelInsuree object that is saved in the database.
     * @param column The column where the value is saved in.
     */
    private Object getColumnValue(ModelInsuree row, int column) {
        Object ret = null;
        switch (this.columns[column]) {
            case ModelInsuree.FIELD_ID:
                ret = row.getID();
                break;
            case ModelInsuree.FIELD_PRENAME:
                ret = row.getPrename();
                break;
            case ModelInsuree.FIELD_SURNAME:
                ret = row.getSurname();
                break;
            case ModelInsuree.FIELD_PRENAME_PARTNER:
                ret = row.getPartner_Prename();
                break;
            case ModelInsuree.FIELD_SURNAME_PARTNER:
                ret = row.getPartner_Surname();
                break;
            case ModelInsuree.FIELD_BIRTHDATE:
                ret = dateFormatter.format(row.getBirthDate());
                break;
            case ModelInsuree.FIELD_BIRTHDATE_PARTNER:
                ret = dateFormatter.format(row.getPartner_BirthDate());
                break;
            case ModelInsuree.FIELD_STREET:
                ret = row.getStreet_Address();
                break;
            case ModelInsuree.FIELD_STREET_NUMBER:
                ret = row.getStreet_Number();
                break;
            case ModelInsuree.FIELD_ZIPCODE:
                ret = row.getZipcode();
                break;
            case ModelInsuree.FIELD_CITY:
                ret = row.getCity();
                break;
            case ModelInsuree.FIELD_TELEPHONE:
                ret = row.getTelephone_Number();
                break;
            case ModelInsuree.FIELD_FAX:
                ret = row.getFax_Number();
                break;
            case ModelInsuree.FIELD_CELLPHONE:
                ret = row.getCellphone_Number();
                break;
            case ModelInsuree.FIELD_EMAIL:
                ret = row.getEmail();
                break;
            case ModelInsuree.FIELD_BANK_NAME:
                ret = row.getBank_Name();
                break;
            case ModelInsuree.FIELD_BANK_IBAN:
                ret = row.getBank_IBAN();
                break;
            case ModelInsuree.FIELD_BANK_BIC:
                ret = row.getBank_BIC();
                break;
            case ModelInsuree.FIELD_JOB:
                ret = row.getJob();
                break;
            case ModelInsuree.FIELD_JOB_PARTNER:
                ret = row.getPartner_Job();
                break;
            case ModelInsuree.FIELD_CONTRACT:
                ret = row.hasContract() ? "ja" : "nein";
                break;
            default:
                ret = "ERROR";
                break;
        }
        return ret;
    }

    /**
     * @param row    The ModelInsuree object that is saved in the database.
     * @param column The column where the value is saved in.
     * @param val    The value that is saved in the cell.
     * @return true if the value was set; otherwise, false.
     */
    private boolean setColumnValue(ModelInsuree row, int column, Object val) {
        switch (this.columns[column]) {
            case ModelInsuree.FIELD_PRENAME:
                row.setPrename((String) val);
                break;
            case ModelInsuree.FIELD_SURNAME:
                row.setSurname((String) val);
                break;
            case ModelInsuree.FIELD_PRENAME_PARTNER:
                row.setPartner_Prename((String) val);
                break;
            case ModelInsuree.FIELD_SURNAME_PARTNER:
                row.setPartner_Surname((String) val);
                break;
            case ModelInsuree.FIELD_BIRTHDATE:
                row.setBirthDate((Date) val);
                break;
            case ModelInsuree.FIELD_BIRTHDATE_PARTNER:
                row.setPartner_BirthDate((Date) val);
                break;
            case ModelInsuree.FIELD_STREET:
                row.setStreet_Address((String) val);
                break;
            case ModelInsuree.FIELD_STREET_NUMBER:
                row.setStreet_Number((String) val);
                break;
            case ModelInsuree.FIELD_ZIPCODE:
                row.setZipcode((String) val);
                break;
            case ModelInsuree.FIELD_CITY:
                row.setCity((String) val);
                break;
            case ModelInsuree.FIELD_TELEPHONE:
                row.setTelephone_Number((String) val);
                break;
            case ModelInsuree.FIELD_FAX:
                row.setFax_Number((String) val);
                break;
            case ModelInsuree.FIELD_CELLPHONE:
                row.setCellphone_Number((String) val);
                break;
            case ModelInsuree.FIELD_EMAIL:
                row.setEmail((String) val);
                break;
            case ModelInsuree.FIELD_BANK_NAME:
                row.setBank_Name((String) val);
                break;
            case ModelInsuree.FIELD_BANK_IBAN:
                row.setBank_IBAN((String) val);
                break;
            case ModelInsuree.FIELD_BANK_BIC:
                row.setBank_BIC((String) val);
                break;
            case ModelInsuree.FIELD_JOB:
                row.setJob((String) val);
                break;
            case ModelInsuree.FIELD_JOB_PARTNER:
                row.setPartner_Job((String) val);
                break;
            case ModelInsuree.FIELD_CONTRACT:
                row.setContract((boolean) val);
                break;
            default:
                return false;
        }
        return true;
    }

    private final class InsureeTableTask extends SwingWorker<Void, ModelInsuree> {

        public InsureeTableTask() {
            rows.clear();
        }

        @Override
        protected Void doInBackground() throws Exception {
            CloseableIterator<ModelInsuree> res = InsureeUtils.getInstance().getResultSet();

            columns = res.getRawResults().getColumnNames();
            List<String> columList = new LinkedList<>(Arrays.asList(res.getRawResults().getColumnNames()));
            columList.remove(0);
            columns = columList.toArray(new String[0]);
            InsureeTableModel.this.fireTableStructureChanged();
            Logger.logDebug("Insuree Columns: " + InsureeTableModel.this.getColumnCount());

            while (res.hasNext()) {
                publish(res.next());
            }
            return null;
        }

        @Override
        protected void process(List<ModelInsuree> chunks
        ) {
            rows.addAll(chunks);
            InsureeTableModel.this.fireTableDataChanged();
            Logger.logDebug("Processing " + chunks.size() + " chunks.");
        }
    }
}
