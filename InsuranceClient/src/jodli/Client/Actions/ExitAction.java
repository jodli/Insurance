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
import src.jodli.Client.log.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by job87 on 3/3/2015.
 */
public class ExitAction extends AbstractAction {

    public ExitAction() {
        super("Beenden");
        putValue(SHORT_DESCRIPTION, "Anwendung schließen.");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Logger.logDebug("Exiting the Application.");

        // Save Database.

        // Close Connections.
        DatabaseUtils.closeDatabase();

        System.exit(0);
    }
}
