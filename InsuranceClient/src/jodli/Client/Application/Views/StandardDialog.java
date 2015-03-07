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

import com.jgoodies.forms.builder.ButtonBarBuilder;
import src.jodli.Client.log.Logger;

import javax.swing.*;
import java.awt.*;

public abstract class StandardDialog {
    private final String m_Title;
    private final JFrame m_Parent;
    private final int m_CloseAction;
    protected JPanel content;
    protected JDialog m_Dialog;
    private JPanel buttonContent;
    private JPanel dialogContent;
    private JButton buttonOK;
    private JButton buttonCancel;

    protected StandardDialog(String title, JFrame parent, CloseAction closeAction) {
        this(title, parent, closeAction, true);
    }

    protected StandardDialog(String title, JFrame parent, CloseAction closeAction, boolean isModal) {
        m_Title = title;
        m_Parent = parent;
        m_CloseAction = closeAction.getValue();

        initDialog(isModal);
    }

    public final void showDialog() {
        Logger.logDebug("Showing dialog.");

        centerDialog();
        m_Dialog.setVisible(true);
    }

    private void centerDialog() {
        Dimension parent = m_Parent.getSize();
        Dimension window = m_Dialog.getSize();

        int x = m_Parent.getLocationOnScreen().x +
                (parent.width / 2 - window.width / 2);
        int y = m_Parent.getLocationOnScreen().y +
                (parent.height / 2 - window.height / 2);

        m_Dialog.setLocation(x, y);
    }

    private void initDialog(boolean isModal) {
        Logger.logDebug("Initializing dialog.");
        m_Dialog = new JDialog(m_Parent, m_Title, isModal);
        m_Dialog.setDefaultCloseOperation(m_CloseAction);
        m_Dialog.setResizable(false);

        buttonOK.addActionListener(e -> {
            Logger.logDebug("Clicked Ok.");
            okAction();
        });

        buttonCancel.addActionListener(e -> {
            Logger.logDebug("Clicked Cancel");
            cancelAction();
        });

        dialogContent.add(getContent());
        buttonContent.add(getButtonBar());
        m_Dialog.getContentPane().add(content);
    }

    private JPanel getButtonBar() {
        ButtonBarBuilder builder = new ButtonBarBuilder();
        builder.setLeftToRight(true);
        builder.addGlue();
        builder.addButton(buttonOK);
        builder.addButton(buttonCancel);
        return builder.getPanel();
    }

    private void cancelAction() {
        dispose();
    }

    protected abstract void okAction();

    public abstract JComponent getContent();

    /**
     * Close the editor dialog.
     */
    public final void dispose() {
        Logger.logDebug("Disposing Dialog.");
        m_Dialog.dispose();
    }

    private void createUIComponents() {
        dialogContent = new JPanel();
        buttonContent = new JPanel();

        buttonOK = new JButton("Speichern");
        buttonCancel = new JButton("Abbrechen");
    }

    protected enum CloseAction {
        DISPOSE(JDialog.DISPOSE_ON_CLOSE),
        HIDE(JDialog.HIDE_ON_CLOSE);

        private final int m_Action;

        private CloseAction(int action) {
            m_Action = action;
        }

        int getValue() {
            return m_Action;
        }
    }
}
