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
package hebe.http;

import org.json.simple.JSONStreamAware;

import hebe.Account;
import hebe.Attachment;
import hebe.Shuffling;
import hebe.HebeException;

import javax.servlet.http.HttpServletRequest;

public final class ShufflingCancel extends CreateTransaction {

    static final ShufflingCancel instance = new ShufflingCancel();

    private ShufflingCancel() {
        super(new APITag[] {APITag.SHUFFLING, APITag.CREATE_TRANSACTION}, "shuffling", "cancellingAccount", "shufflingStateHash");
    }

    @Override
    protected JSONStreamAware processRequest(HttpServletRequest req) throws HebeException {
        Shuffling shuffling = ParameterParser.getShuffling(req);
        long cancellingAccountId = ParameterParser.getAccountId(req, "cancellingAccount", false);
        byte[] shufflingStateHash = ParameterParser.getBytes(req, "shufflingStateHash", true);
        String secretPhrase = ParameterParser.getSecretPhrase(req, true);
        Attachment.ShufflingCancellation attachment = shuffling.revealKeySeeds(secretPhrase, cancellingAccountId, shufflingStateHash);
        Account account = ParameterParser.getSenderAccount(req);
        return createTransaction(req, account, attachment);
    }
}
