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

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * Abstract Table Model to be implemented by Database Models.
 *
 * @author Jan-Olaf Becker <job87@web.de>
 * @param <T> Database Model Class.
 */
public abstract class TableModel<T> extends AbstractTableModel {

    protected final List<T> rows;
    protected String[] columns;

    /**
     * Create a new TableModel object. Initializes the rows list and the column
     * names array.
     */
    protected TableModel() {
        this.rows = new ArrayList();
        this.columns = null;
    }

    @Override
    public int getRowCount() {
        return this.rows.size();
    }

    @Override
    public int getColumnCount() {
        if (this.columns != null) {
            return this.columns.length;
        }
        return 0;
    }

    @Override
    public String getColumnName(int i) {
        return columns[i];
    }
}
