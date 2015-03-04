/*
 * Copyright (C) 2013 Jan-Olaf Becker
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package src.jodli.Client.Utilities;

import src.jodli.Client.Updater.SelfUpdate;
import src.jodli.Client.Updater.UpdateInfo;
import src.jodli.Client.log.Logger;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Async update downloader updating a ProgressBar.
 *
 * @author Jan-Olaf Becker
 */
public class DownloadTask extends SwingWorker<Void, Void> {
    private static final int BUFFER_SIZE = 4096;
    private UpdateInfo gui;
    private URL downloadURL;
    private String tempUpdatePath;
    private String executablePath;
    private String buildNumber;

    /**
     * Creates a new DownloadTask object.
     *
     * @param gui            Gui to update and dispose.
     * @param downloadURL    Download file from this URL.
     * @param tempUpdatePath Save downloaded file in this path.
     * @param executablePath Executable currently running this application.
     * @param buildNumber    Latest build number corresponding to the downloaded update.
     */
    public DownloadTask(UpdateInfo gui, URL downloadURL, String tempUpdatePath,
                        String executablePath, String buildNumber) {
        this.gui = gui;
        this.downloadURL = downloadURL;
        this.tempUpdatePath = tempUpdatePath;
        this.executablePath = executablePath;
        this.buildNumber = buildNumber;
    }

    /**
     * Executed in background thread
     */
    @Override
    protected Void doInBackground() throws Exception {
        try {
            HttpURLConnection httpcon = (HttpURLConnection) downloadURL
                    .openConnection();
            this.tempUpdatePath += downloadURL.getFile().substring(
                    downloadURL.getFile().lastIndexOf('/') + 1);
            Logger.logDebug("Starting download of update from: "
                    + downloadURL.toString() + " to file: " + tempUpdatePath);
            File tempfile = new File(tempUpdatePath);
            tempfile.getParentFile().mkdirs();

            // opens an output stream to save into file
            FileOutputStream os = new FileOutputStream(tempfile);
            InputStream is = httpcon.getInputStream();

            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead = -1;
            long totalBytesRead = 0;
            int percentCompleted = 0;
            long fileSize = httpcon.getContentLengthLong();

            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
                totalBytesRead += bytesRead;
                percentCompleted = (int) (totalBytesRead * 100 / fileSize);

                setProgress(percentCompleted);
            }

            is.close();
            os.close();
            httpcon.disconnect();
        } catch (IOException ex) {
            Logger.logError("Error downloading update file.", ex);
            setProgress(0);
            cancel(true);
        }
        return null;
    }

    /**
     * Executed in Swing's event dispatching thread
     */
    @Override
    protected void done() {
        if (!isCancelled()) {
            if (JOptionPane
                    .showOptionDialog(
                            gui,
                            "Update successfully downloaded.\n Do you want to install now?",
                            "Update complete", JOptionPane.YES_NO_OPTION,
                            JOptionPane.PLAIN_MESSAGE, null, null, null) == 0) {
                SelfUpdate.runUpdate(this.executablePath, this.tempUpdatePath,
                        this.buildNumber);
            } else {
                gui.dispose();
            }
        } else {
            Logger.logWarn("Update downloading cancelled.");
        }
    }
}
