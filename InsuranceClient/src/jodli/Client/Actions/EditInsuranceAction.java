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
import src.jodli.Client.Application.Views.InsuranceEditorView;
import src.jodli.Client.Application.Views.StandardDialog;
import src.jodli.Client.log.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by job87 on 3/7/2015.
 */
public class EditInsuranceAction extends AbstractAction {

    public static final String TITLE = "Versicherung bearbeiten";

    private IEditorView m_EditInsuranceView;
    private JFrame m_Frame;
    private InsuranceEditor m_InsuranceEditor;

    public EditInsuranceAction(JFrame frame, IEditorView editInsuranceView) {
        super(TITLE);
        m_Frame = frame;
        m_EditInsuranceView = editInsuranceView;
        putValue(SHORT_DESCRIPTION, "Bestehende Versicherung bearbeiten.");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Logger.logDebug("Showing Edit Insurance Dialog.");
        if (m_InsuranceEditor == null) {
            m_InsuranceEditor = new InsuranceEditor(TITLE, m_Frame);
        }
        m_EditInsuranceView.loadSettings();
        if (((InsuranceEditorView) m_EditInsuranceView).getInsuree() != null) {
            m_InsuranceEditor.showDialog();
        }
    }

    private JComponent getEditorContent() {
        return m_EditInsuranceView.getContent();
    }

    private boolean saveInsurance() {
        Logger.logDebug("Saving Insurance Data.");
        return m_EditInsuranceView.saveSettings();
    }

    private final class InsuranceEditor extends StandardDialog {

        InsuranceEditor(String title, JFrame parent) {
            super(title, parent, CloseAction.HIDE);
            m_Dialog.setSize(650, 480);
        }

        @Override
        protected void okAction() {
            if (saveInsurance()) {
                Logger.logDebug("Saved Insurance.");
                dispose();
            } else {
                Logger.logWarn("Could not save Insurance. Please try again.");
            }
        }

        @Override
        public JComponent getContent() {
            return getEditorContent();
        }
    }
}
