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
import java.util.List;
import javax.swing.SwingWorker;
import src.jodli.Client.Utilities.DatabaseModels.ModelInsuree;
import src.jodli.Client.Utilities.InsureeUtils;
import src.jodli.Client.log.Logger;

/**
 * TableModel to be used to display the Insuree table in the database.
 *
 * @author Jan-Olaf Becker <job87@web.de>
 */
public class InsureeTableModel extends TableModel<ModelInsuree> {

    /**
     * Creates a new InsureeTableModel object. Starts the SwingWorker to process
     * the rows and columns in the database.
     */
    public InsureeTableModel() {
        new SwingWorker<Void, ModelInsuree>() {

            @Override
            protected Void doInBackground() throws Exception {
                CloseableIterator<ModelInsuree> res = InsureeUtils.getResultSet();

                columns = res.getRawResults().getColumnNames();
                InsureeTableModel.this.fireTableStructureChanged();
                Logger.logInfo("Insuree Columns: " + InsureeTableModel.this.getColumnCount());

                while (res.hasNext()) {
                    publish(res.next());
                }
                return null;
            }

            @Override
            protected void process(List<ModelInsuree> chunks) {
                rows.addAll(chunks);
                InsureeTableModel.this.fireTableDataChanged();
                Logger.logInfo("Processing " + chunks.size() + " chunks.");
            }
        }.execute();
    }

    @Override
    public Object getValueAt(int row, int column) {
        return getColumnValue(this.rows.get(row), column);
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (this.setColumnValue(this.rows.get(rowIndex), columnIndex, aValue)) {
            //TODO: update value in database.
            this.fireTableCellUpdated(rowIndex, columnIndex);
        }
        Logger.logInfo("row: " + rowIndex + " col: " + columnIndex + " val: " + aValue.toString());
    }

    /**
     * Gets the value of the column of the specified row.
     *
     * @param row The ModelInsuree object that is saved in the database.
     * @param column The column where the value is saved in.
     */
    private Object getColumnValue(ModelInsuree row, int column) {
        Object ret;
        switch (this.columns[column]) {
            case "ID":
                ret = row.getID();
                break;
            case "Prename":
                ret = row.getPrename();
                break;
            case "Surname":
                ret = row.getSurname();
                break;
            case "Partner_Prename":
                ret = row.getPartner_Prename();
                break;
            case "Partner_Surname":
                ret = row.getPartner_Surname();
                break;
            case "BirthDate":
                ret = row.getBirthDate();
                break;
            case "Partner_BirthDate":
                ret = row.getPartner_BirthDate();
                break;
            case "Street_Address":
                ret = row.getStreet_Address();
                break;
            case "Street_Number":
                ret = row.getStreet_Number();
                break;
            case "Zipcode":
                ret = row.getZipcode();
                break;
            case "City":
                ret = row.getCity();
                break;
            case "Telephone_Number":
                ret = row.getTelephone_Number();
                break;
            case "Fax_Number":
                ret = row.getFax_Number();
                break;
            case "Cellphone_Number":
                ret = row.getCellphone_Number();
                break;
            case "Email":
                ret = row.getEmail();
                break;
            case "Bank_Name":
                ret = row.getBank_Name();
                break;
            case "Bank_IBAN":
                ret = row.getBank_IBAN();
                break;
            case "Bank_BIC":
                ret = row.getBank_BIC();
                break;
            case "Job":
                ret = row.getJob();
                break;
            case "Partner_Job":
                ret = row.getPartner_Job();
                break;
            case "Contract":
                ret = row.isContract();
                break;
            default:
                ret = "ERROR";
                break;
        }
        return ret;
    }

    /**
     *
     * @param row The ModelInsuree object that is saved in the database.
     * @param column The column where the value is saved in.
     * @param val The value that is saved in the cell.
     * @return true if the value was set; otherwise, false.
     */
    private boolean setColumnValue(ModelInsuree row, int column, Object val) {
        switch (this.columns[column]) {
            case "Prename":
                row.setPrename((String) val);
                break;
            case "Surname":
                row.setSurname((String) val);
                break;
            case "Partner_Prename":
                row.setPartner_Prename((String) val);
                break;
            case "Partner_Surname":
                row.setPartner_Surname((String) val);
                break;
            case "BirthDate":
                row.setBirthDate((String) val);
                break;
            case "Partner_BirthDate":
                row.setPartner_BirthDate((String) val);
                break;
            case "Street_Address":
                row.setStreet_Address((String) val);
                break;
            case "Street_Number":
                row.setStreet_Number((String) val);
                break;
            case "Zipcode":
                row.setZipcode((String) val);
                break;
            case "City":
                row.setCity((String) val);
                break;
            case "Telephone_Number":
                row.setTelephone_Number((String) val);
                break;
            case "Fax_Number":
                row.setFax_Number((String) val);
                break;
            case "Cellphone_Number":
                row.setCellphone_Number((String) val);
                break;
            case "Email":
                row.setEmail((String) val);
                break;
            case "Bank_Name":
                row.setBank_Name((String) val);
                break;
            case "Bank_IBAN":
                row.setBank_IBAN((String) val);
                break;
            case "Bank_BIC":
                row.setBank_BIC((String) val);
                break;
            case "Job":
                row.setJob((String) val);
                break;
            case "Partner_Job":
                row.setPartner_Job((String) val);
                break;
            case "Contract":
                row.setContract((boolean) val);
                break;
            default:
                return false;
        }
        return true;
    }
}
