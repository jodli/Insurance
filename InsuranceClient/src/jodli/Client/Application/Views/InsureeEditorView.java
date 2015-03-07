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

import src.jodli.Client.Utilities.DatabaseModels.ModelInsuree;
import src.jodli.Client.Utilities.InsureeUtils;
import src.jodli.Client.log.Logger;

import javax.swing.*;
import java.util.Observable;

/**
 * Created by job87 on 3/6/2015.
 */
public class InsureeEditorView extends Observable implements IEditorView {
    private JPanel content;
    private JTextField telephoneTextField;
    private JTextField streetTextField;
    private JTextField numberTextField;
    private JTextField zipcodeTextField;
    private JTextField cityTextField;
    private JTextField faxTextField;
    private JTextField cellphoneTextField;
    private JTextField emailTextField;
    private JTextField bankTextField;
    private JTextField ibanTextField;
    private JTextField bicTextField;
    private JCheckBox contractCheckBox;
    private JTextField surnameTextField;
    private JTextField prenameTextField;
    private JTextField jobTextField;
    private int m_InsureeID;

    private void createUIComponents() {
        content = new JPanel();
        m_InsureeID = -1;
    }

    @Override
    public JComponent getContent() {
        return content;
    }

    public boolean saveSettings() {
        ModelInsuree m = new ModelInsuree();
        return InsureeUtils.getInstance().setValue(m);
    }

    @Override
    public void loadSettings() {
        if (m_InsureeID < 0) {
            Logger.logDebug("Creating new Insuree.");
        } else {
            Logger.logDebug("Getting Insuree data for Insuree ID " + m_InsureeID);
            ModelInsuree m = InsureeUtils.getInstance().getValue(m_InsureeID);
        }
    }

    public void setInsureeID(int insureeID) {
        m_InsureeID = insureeID;
    }

}
