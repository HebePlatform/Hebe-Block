/*
 * Copyright © 2013-2016 The NXT Core Developers.
 * Copyright © 2016-2017 Jelurida IP B.V.
 *  *
 * See the LICENSE.txt file at the top-level directory of this distribution
 * for licensing information.
 *
 * Unless otherwise agreed in a custom licensing agreement with Jelurida B.V.,
 * no part of the NXT software, including this file, may be copied, modified,
 * propagated, or distributed except according to the terms contained in the
 * LICENSE.txt file.
 *
 * Removal or modification of this copyright notice is prohibited.
 *
 */
/*
 * Copyright © 2017-2019 HebeBlock.
 */
package hebe.env;

import javax.swing.*;

import hebe.util.Logger;

import java.io.File;
import java.net.URI;

public class DesktopMode implements RuntimeMode {

    private DesktopSystemTray desktopSystemTray;
    private Class desktopApplication;

    @Override
    public void init() {
        LookAndFeel.init();
        desktopSystemTray = new DesktopSystemTray();
        SwingUtilities.invokeLater(desktopSystemTray::createAndShowGUI);
    }

    @Override
    public void setServerStatus(ServerStatus status, URI wallet, File logFileDir) {
        desktopSystemTray.setToolTip(new SystemTrayDataProvider(status.getMessage(), wallet, logFileDir));
    }

    @Override
    public void launchDesktopApplication() {
        Logger.logInfoMessage("Launching desktop wallet");
        try {
            desktopApplication = Class.forName("hebedesktop.DesktopApplication");
            desktopApplication.getMethod("launch").invoke(null);
        } catch (ReflectiveOperationException e) {
            Logger.logInfoMessage("hebedesktop.DesktopApplication failed to launch", e);
        }
    }

    @Override
    public void shutdown() {
        desktopSystemTray.shutdown();
        if (desktopApplication == null) {
            return;
        }
        try {
            desktopApplication.getMethod("shutdown").invoke(null);
        } catch (ReflectiveOperationException e) {
            Logger.logInfoMessage("hebedesktop.DesktopApplication failed to shutdown", e);
        }
    }

    @Override
    public void alert(String message) {
        desktopSystemTray.alert(message);
    }
}
