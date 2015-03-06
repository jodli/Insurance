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

import src.jodli.Client.Application.Views.IEditorView;
import src.jodli.Client.Application.Views.StandardDialog;
import src.jodli.Client.log.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by job87 on 3/6/2015.
 */
public class EditInsureeAction extends AbstractAction {

    public static final String TITLE = "Kunde bearbeiten";

    private IEditorView m_EditInsureeView;
    private JFrame m_Frame;
    private InsureeEditor m_InsureeEditor;

    public EditInsureeAction(JFrame frame, IEditorView editInsureeView) {
        super(TITLE);
        m_Frame = frame;
        m_EditInsureeView = editInsureeView;
        putValue(SHORT_DESCRIPTION, "Bestehenden Kunden bearbeiten.");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Logger.logDebug("Showing Edit Insuree Dialog.");
        if (m_InsureeEditor == null) {
            m_InsureeEditor = new InsureeEditor(TITLE, m_Frame);
        }
        m_EditInsureeView.loadSettings();
        m_InsureeEditor.showDialog();
    }

    private JComponent getEditorContent() {
        JPanel content = new JPanel();
        content.add(m_EditInsureeView.getContent());
        return content;
    }

    private boolean saveInsuree() {
        Logger.logDebug("Saving Insuree Data.");
        m_EditInsureeView.saveSettings();
        return false;
    }

    private final class InsureeEditor extends StandardDialog {

        InsureeEditor(String title, JFrame parent) {
            super(title, parent, CloseAction.HIDE);
            m_Dialog.setSize(800, 600);
            m_Dialog.setResizable(true);
        }

        @Override
        protected void okAction() {
            if (saveInsuree()) {
                Logger.logDebug("Saved Insuree.");
                dispose();
            } else {
                Logger.logWarn("Could not save Insuree. Please try again.");
            }
        }

        @Override
        public JComponent getContent() {
            return getEditorContent();
        }
    }
}
