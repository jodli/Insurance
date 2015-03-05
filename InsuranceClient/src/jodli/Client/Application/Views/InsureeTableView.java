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

package src.jodli.Client.Application.Views;

import src.jodli.Client.TableModels.InsureeTableModel;
import src.jodli.Client.TableModels.TableModel;
import src.jodli.Client.log.Logger;

import javax.swing.*;

/**
 * Created by job87 on 3/4/2015.
 */
public class InsureeTableView extends StandardTableView {

    private static final String m_Title = "Kunden";

    public InsureeTableView(TableModel tableModel) {
        super(tableModel);

        m_ListSelectionModel = m_Table.getSelectionModel();

        m_ListSelectionModel.addListSelectionListener(e -> {
            ListSelectionModel listSelectionModel = (ListSelectionModel) e.getSource();

            if (!(listSelectionModel.getValueIsAdjusting() || listSelectionModel.isSelectionEmpty())) {
                int selectedId = ((InsureeTableModel) m_Table.getModel()).getId(m_Table.convertRowIndexToModel(m_Table.getSelectedRow()));

                Logger.logDebug("Insuree table selection changed. Selected ID " + selectedId);
                // Notify Insurance view.
                setChanged();
                notifyObservers(selectedId);
            }
        });

        m_Table.setSelectionModel(m_ListSelectionModel);
    }

    @Override
    public String getTabTitle() {
        return m_Title;
    }
}
