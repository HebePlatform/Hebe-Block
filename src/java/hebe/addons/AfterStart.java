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
package hebe.addons;

import hebe.HEBE;
import hebe.util.Logger;
import hebe.util.ThreadPool;

public final class AfterStart implements AddOn {

    @Override
    public void init() {
        String afterStartScript = HEBE.getStriheberoperty("hebe.afterStartScript");
        if (afterStartScript != null) {
            ThreadPool.runAfterStart(() -> {
                try {
                    Runtime.getRuntime().exec(afterStartScript);
                } catch (Exception e) {
                    Logger.logErrorMessage("Failed to run after start script: " + afterStartScript, e);
                }
            });
        }
    }

}
