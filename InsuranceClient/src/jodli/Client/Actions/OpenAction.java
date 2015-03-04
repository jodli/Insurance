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

import src.jodli.Client.Utilities.DatabaseUtils;
import src.jodli.Client.Utilities.FileUtils;
import src.jodli.Client.log.Logger;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Objects;

/**
 * Created by job87 on 3/3/2015.
 */
public class OpenAction extends AbstractAction {

    private JFrame m_Frame;

    public OpenAction(JFrame frame) {
        super("Öffnen");
        putValue(SHORT_DESCRIPTION, "Datenbank öffnen.");
        m_Frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Logger.logDebug("Opening Database at path: ");
        showDialog();
    }

    private void showDialog() {
        String path = openDialog();
        if (path != null) {
            DatabaseUtils.openDatabase(path);
        }
    }

    private String openDialog() {
        JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home"));
        fileChooser.setDialogTitle("Datenbank öffnen");
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        fileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return (Objects.equals(FileUtils.getExtension(f), FileUtils.SQLITE_EXTENSION) || f.isDirectory());
            }

            @Override
            public String getDescription() {
                return "Datenbank Dateien (.sqlite)";
            }
        });

        if (fileChooser.showOpenDialog(m_Frame) == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getAbsolutePath();
        }
        return null;
    }
}
