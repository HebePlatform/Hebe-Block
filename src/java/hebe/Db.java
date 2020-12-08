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
package hebe;

import hebe.db.BasicDb;
import hebe.db.TransactionalDb;

public final class Db {

    public static final String PREFIX = Constants.isTestnet ? "hebe.testDb" : "hebe.db";
    public static final TransactionalDb db = new TransactionalDb(new BasicDb.DbProperties()
            .maxCacheSize(HEBE.getIntProperty("hebe.dbCacheKB"))
            .dbUrl(HEBE.getStriheberoperty(PREFIX + "Url"))
            .dbType(HEBE.getStriheberoperty(PREFIX + "Type"))
            .dbDir(HEBE.getStriheberoperty(PREFIX + "Dir"))
            .dbParams(HEBE.getStriheberoperty(PREFIX + "Params"))
            .dbUsername(HEBE.getStriheberoperty(PREFIX + "Username"))
            .dbPassword(HEBE.getStriheberoperty(PREFIX + "Password", null, true))
            .maxConnections(HEBE.getIntProperty("hebe.maxDbConnections"))
            .loginTimeout(HEBE.getIntProperty("hebe.dbLoginTimeout"))
            .defaultLockTimeout(HEBE.getIntProperty("hebe.dbDefaultLockTimeout") * 1000)
            .maxMemoryRows(HEBE.getIntProperty("hebe.dbMaxMemoryRows"))
    );

    static void init() {
        db.init(new HebeDbVersion());
    }

    static void shutdown() {
        db.shutdown();
    }

    private Db() {} // never

}
