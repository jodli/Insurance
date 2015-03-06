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

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import src.jodli.Client.Updater.UpdateChecker;
import src.jodli.Client.Utilities.ESetting;
import src.jodli.Client.Utilities.SettingsUtils;
import src.jodli.Client.log.ELogType;
import src.jodli.Client.log.Logger;

import javax.swing.*;
import java.util.Observable;

/**
 * Created by job87 on 3/2/2015.
 */
public class GeneralSettingsView extends Observable implements IEditorView, ITabbedView {

    private final static String m_TitleGeneral = "Allgemein";

    private JPanel content;
    private JCheckBox chk_CheckUpdate;
    private JComboBox cb_LogType;
    private JButton btn_CheckUpdate;

    public GeneralSettingsView() {
    }

    private void createUIComponents() {
        chk_CheckUpdate = new JCheckBox();
        cb_LogType = new JComboBox(ELogType.values());
        btn_CheckUpdate = new JButton();
        btn_CheckUpdate.addActionListener(e -> UpdateChecker.updateApp(true));
    }

    @Override
    public JComponent getContent() {
        return content;
    }

    @Override
    public String getTabTitle() {
        return m_TitleGeneral;
    }

    @Override
    public boolean saveSettings() {
        Logger.logDebug("Saving General Settings.");
        //save settings to prefs
        boolean changed = false;
        changed |= SettingsUtils.setValue(ESetting.CHECKUPDATE, Boolean.toString(chk_CheckUpdate.isSelected()));
        changed |= SettingsUtils.setValue(ESetting.LOGTYPE, cb_LogType.getSelectedItem().toString().toUpperCase());

        if (changed) {
            setChanged();
            notifyObservers();
        }
        return changed;
    }

    @Override
    public void loadSettings() {
        Logger.logDebug("Loading General Settings.");
        //load settings from database
        chk_CheckUpdate.setSelected(Boolean.parseBoolean(SettingsUtils.getValue(ESetting.CHECKUPDATE)));
        cb_LogType.setSelectedItem(ELogType.valueOf(SettingsUtils.getValue(ESetting.LOGTYPE)));
    }

}
