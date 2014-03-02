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

import com.j256.ormlite.dao.CloseableIterator;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingWorker;
import javax.swing.table.AbstractTableModel;
import src.jodli.Client.Utilities.Insuree;
import src.jodli.Client.Utilities.InsureeUtils;

/**
 *
 * @author Jan-Olaf Becker <job87@web.de>
 */
public class InsureeTableModel extends AbstractTableModel {

    private final List<ModelInsuree> insurees;

    public InsureeTableModel() {
        this.insurees = new ArrayList<ModelInsuree>();

        new SwingWorker<Void, ModelInsuree>() {

            @Override
            protected Void doInBackground() throws Exception {
                CloseableIterator<ModelInsuree> res = InsureeUtils.getResultSet();
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
        return InsureeUtils.getColumnCount();
    }

    public Object getValueAt(int row, int column) {
        Object ret = null;
        ModelInsuree r = this.insurees.get(row);

        switch (Insuree.fromKey(column)) {
            case PreName:
                ret = r.getPrename();
                break;
            case Default:
                ret = "";
                break;
        }
        return ret;
    }

    @Override
    public String getColumnName(int i) {
        String ret = "";
        Insuree ins = Insuree.fromKey(i);

        if (ins != null) {
            ret = ins.toString();
        }
        return ret;
    }
}
