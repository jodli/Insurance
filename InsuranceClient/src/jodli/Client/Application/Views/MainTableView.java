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

import org.jdesktop.swingx.JXTable;
import src.jodli.Client.TableModels.InsureeTableModel;

import javax.swing.*;

/**
 * Created by job87 on 3/2/2015.
 */
public class MainTableView implements IView {
    private JPanel content;
    private JXTable table;

    private void createUIComponents() {
        table = new JXTable(new InsureeTableModel());
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.packTable(10);
    }

    private void $$$setupUI$$$() {
        createUIComponents();
    }

    @Override
    public JPanel getContent() {
        return content;
    }
}
