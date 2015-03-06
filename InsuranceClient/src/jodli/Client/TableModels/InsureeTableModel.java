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
            case "ID":
                ret = row.getID();
                break;
            case "Vorname":
                ret = row.getPrename();
                break;
            case "Nachname":
                ret = row.getSurname();
                break;
            case "Vorname Partner":
                ret = row.getPartner_Prename();
                break;
            case "Nachname Partner":
                ret = row.getPartner_Surname();
                break;
            case "Geburtsdatum":
                ret = dateFormatter.format(row.getBirthDate());
                break;
            case "GD Partner":
                ret = dateFormatter.format(row.getPartner_BirthDate());
                break;
            case "Straße":
                ret = row.getStreet_Address();
                break;
            case "Hausnummer":
                ret = row.getStreet_Number();
                break;
            case "PLZ":
                ret = row.getZipcode();
                break;
            case "Wohnort":
                ret = row.getCity();
                break;
            case "Telefon":
                ret = row.getTelephone_Number();
                break;
            case "Fax":
                ret = row.getFax_Number();
                break;
            case "Handy":
                ret = row.getCellphone_Number();
                break;
            case "Email":
                ret = row.getEmail();
                break;
            case "Bank Name":
                ret = row.getBank_Name();
                break;
            case "IBAN":
                ret = row.getBank_IBAN();
                break;
            case "BIC":
                ret = row.getBank_BIC();
                break;
            case "Job":
                ret = row.getJob();
                break;
            case "Job Partner":
                ret = row.getPartner_Job();
                break;
            case "Vollmacht":
                ret = row.isContract() ? "ja" : "nein";
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
            case "Vorname":
                row.setPrename((String) val);
                break;
            case "Nachname":
                row.setSurname((String) val);
                break;
            case "Vorname Partner":
                row.setPartner_Prename((String) val);
                break;
            case "Nachname Partner":
                row.setPartner_Surname((String) val);
                break;
            case "Geburtsdatum":
                row.setBirthDate((Date) val);
                break;
            case "GD Partner":
                row.setPartner_BirthDate((Date) val);
                break;
            case "Straße":
                row.setStreet_Address((String) val);
                break;
            case "Hausnummer":
                row.setStreet_Number((String) val);
                break;
            case "PLZ":
                row.setZipcode((String) val);
                break;
            case "Wohnort":
                row.setCity((String) val);
                break;
            case "Telefon":
                row.setTelephone_Number((String) val);
                break;
            case "Fax":
                row.setFax_Number((String) val);
                break;
            case "Handy":
                row.setCellphone_Number((String) val);
                break;
            case "Email":
                row.setEmail((String) val);
                break;
            case "Bank Name":
                row.setBank_Name((String) val);
                break;
            case "IBAN":
                row.setBank_IBAN((String) val);
                break;
            case "BIC":
                row.setBank_BIC((String) val);
                break;
            case "Job":
                row.setJob((String) val);
                break;
            case "Job Partner":
                row.setPartner_Job((String) val);
                break;
            case "Vollmacht":
                row.setContract((boolean) val);
                break;
            default:
                return false;
        }
        return true;
    }

    private final class InsureeTableTask extends SwingWorker<Void, ModelInsuree> {

        public InsureeTableTask() {
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
