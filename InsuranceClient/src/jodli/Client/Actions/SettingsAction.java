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

package src.jodli.Client.Actions;

import src.jodli.Client.Application.Views.ISettingsView;
import src.jodli.Client.Application.Views.StandardDialog;
import src.jodli.Client.log.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * Created by job87 on 3/3/2015.
 */
public class SettingsAction extends AbstractAction {

    private JFrame m_Frame;
    private List<ISettingsView> m_SettingsViews;
    private SettingsDialog m_SettingsDialog;

    public SettingsAction(JFrame frame, List<ISettingsView> views) {
        super("Einstellungen");
        m_Frame = frame;
        m_SettingsViews = views;
        putValue(SHORT_DESCRIPTION, "Einstellungen vornehmen.");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Logger.logDebug("Showing user settings dialog.");
        if (m_SettingsDialog == null) {
            m_SettingsDialog = new SettingsDialog("Einstellungen", m_Frame);
        }
        m_SettingsDialog.showDialog();
    }

    private void saveSettings() {
        Logger.logDebug("Saving settings.");
        for (ISettingsView view : m_SettingsViews) {
            view.saveSettings();
        }
    }

    private void loadSettings() {
        Logger.logDebug("Loading settings.");
        for (ISettingsView view : m_SettingsViews) {
            view.loadSettings();
        }
    }

    private JComponent getSettingsContent() {
        JTabbedPane content = new JTabbedPane();
        content.setTabPlacement(SwingConstants.TOP);

        for (ISettingsView view : m_SettingsViews) {
            JComponent gui = view.getContent();
            content.addTab(view.getTabTitle(), gui);
            loadSettings();
        }
        return content;
    }

    private final class SettingsDialog extends StandardDialog {

        SettingsDialog(String title, JFrame parent) {
            super(title, parent, StandardDialog.CloseAction.HIDE);
        }

        @Override
        protected void okAction() {
            saveSettings();
            dispose();
        }

        public JComponent getContent() {
            JPanel content = new JPanel();
            content.add(getSettingsContent());
            return content;
        }
    }
}
