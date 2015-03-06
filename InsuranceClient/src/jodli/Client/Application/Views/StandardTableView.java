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

import net.coderazzi.filters.gui.AutoChoices;
import net.coderazzi.filters.gui.FilterSettings;
import net.coderazzi.filters.gui.TableFilterHeader;
import org.jdesktop.swingx.JXTable;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.util.Observable;

/**
 * Created by job87 on 3/2/2015.
 */
public abstract class StandardTableView extends Observable implements IView {
    protected ListSelectionModel m_ListSelectionModel;
    protected JXTable m_Table;
    protected TableModel m_TableModel;

    private JPanel content;
    private TableFilterHeader m_FilterHeader;

    protected StandardTableView(TableModel tableModel) {
        m_TableModel = tableModel;
    }

    private void createUIComponents() {
        m_Table = new JXTable(m_TableModel) {
            @Override
            public boolean getScrollableTracksViewportWidth() {
                return getPreferredSize().width < getParent().getWidth();
            }
        };
        m_Table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        m_Table.setAutoResizeMode(JXTable.AUTO_RESIZE_OFF);
        m_Table.packTable(10);

        m_FilterHeader = new TableFilterHeader(m_Table, AutoChoices.ENABLED);
        FilterSettings.ignoreCase = true;
    }

    private void $$$setupUI$$$() {
        createUIComponents();
    }

    @Override
    public JComponent getContent() {
        return content;
    }

    @Override
    public abstract String getTabTitle();
}
