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
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingWorker;
import javax.swing.table.AbstractTableModel;
import src.jodli.Client.Utilities.DatabaseModels.ModelInsuree;
import src.jodli.Client.Utilities.InsureeUtils;

/**
 *
 * @author Jan-Olaf Becker <job87@web.de>
 */
public class InsureeTableModel extends AbstractTableModel {

    private final List<ModelInsuree> insurees;
    private String[] columns = null;

    public InsureeTableModel() {
        this.insurees = new ArrayList();

        new SwingWorker<Void, ModelInsuree>() {

            @Override
            protected Void doInBackground() throws Exception {
                CloseableIterator<ModelInsuree> res = InsureeUtils.getResultSet();

                columns = res.getRawResults().getColumnNames();
                InsureeTableModel.this.fireTableStructureChanged();

                while (res.hasNext()) {
                    publish(res.next());
                }
                return null;
            }

            @Override
            protected void process(List<ModelInsuree> chunks) {
                insurees.addAll(chunks);
                InsureeTableModel.this.fireTableDataChanged();
            }
        }.execute();
    }

    public int getRowCount() {
        return this.insurees.size();
    }

    public int getColumnCount() {
        if (columns != null) {
            return columns.length;
        }
        return 0;
    }

    public Object getValueAt(int row, int column) {
        Object ret = null;
        ModelInsuree r = this.insurees.get(row);

        switch (columns[column]) {
            case "ID":
                ret = r.getID();
                break;
            case "Prename":
                ret = r.getPrename();
                break;
            case "Surname":
                ret = r.getSurname();
                break;
            case "Partner_Prename":
                ret = r.getPartner_Prename();
                break;
            case "Partner_Surname":
                ret = r.getPartner_Surname();
                break;
            case "BirthDate":
                ret = r.getBirthDate();
                break;
            case "Partner_BirthDate":
                ret = r.getPartner_BirthDate();
                break;
            case "Street_Address":
                ret = r.getStreet_Address();
                break;
            case "Street_Number":
                ret = r.getStreet_Number();
                break;
            case "Zipcode":
                ret = r.getZipcode();
                break;
            case "City":
                ret = r.getCity();
                break;
            case "Telephone_Number":
                ret = r.getTelephone_Number();
                break;
            case "Fax_Number":
                ret = r.getFax_Number();
                break;
            case "Cellphone_Number":
                ret = r.getCellphone_Number();
                break;
            case "Email":
                ret = r.getEmail();
                break;
            case "Bank_Name":
                ret = r.getBank_Name();
                break;
            case "Bank_IBAN":
                ret = r.getBank_IBAN();
                break;
            case "Bank_BIC":
                ret = r.getBank_BIC();
                break;
            case "Job":
                ret = r.getJob();
                break;
            case "Partner_Job":
                ret = r.getPartner_Job();
                break;
            case "Contract":
                ret = r.isContract();
                break;
            default:
                ret = "ERROR";
                break;
        }

        return ret;
    }

    @Override
    public String getColumnName(int i) {
        return columns[i];
    }
}
