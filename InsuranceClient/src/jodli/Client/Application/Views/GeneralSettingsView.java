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

import src.jodli.Client.Utilities.Setting;
import src.jodli.Client.Utilities.SettingsUtils;
import src.jodli.Client.log.LogType;

import javax.swing.*;

/**
 * Created by job87 on 3/2/2015.
 */
public class GeneralSettingsView implements IView {

    private final static String m_TitleGeneral = "Allgemein";

    private JPanel content;
    private JCheckBox chk_CheckUpdate;
    private JComboBox cb_LogType;

    private void createUIComponents() {
        chk_CheckUpdate = new JCheckBox();
        cb_LogType = new JComboBox(LogType.values());

        loadSettings();
    }

    private void $$$setupUI$$$() {
        createUIComponents();
    }

    @Override
    public JPanel getContent() {
        return content;
    }

    public String getTabTitle() {
        return m_TitleGeneral;
    }

    public void saveSettings() {
        //save settings to database
    }

    public boolean getCheckUpdate() {
        return chk_CheckUpdate.isSelected();
    }

    private void loadSettings() {
        //load settings from database
        String checkUpdate = SettingsUtils.getValue(Setting.CHECKUPDATE);
        chk_CheckUpdate.setSelected(Boolean.parseBoolean(checkUpdate));

        String logtype = SettingsUtils.getValue(Setting.LOGTYPE);
        cb_LogType.setSelectedItem(LogType.valueOf(logtype));
    }
}
