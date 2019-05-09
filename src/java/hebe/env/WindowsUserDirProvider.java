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

import java.nio.file.Paths;

public class WindowsUserDirProvider extends DesktopUserDirProvider {

    private static final String HEBE_USER_HOME = Paths.get(System.getProperty("user.home"), "AppData", "Roaming", "HEBE").toString();

    @Override
    public String getUserHomeDir() {
        return HEBE_USER_HOME;
    }
}
