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

import org.json.simple.JSONObject;
import org.json.simple.JSONStreamAware;

import hebe.PhasingPoll;
import hebe.HebeException;

import javax.servlet.http.HttpServletRequest;

public class GetAccountPhasedTransactionCount extends APIServlet.APIRequestHandler {
    static final GetAccountPhasedTransactionCount instance = new GetAccountPhasedTransactionCount();

    private GetAccountPhasedTransactionCount() {
        super(new APITag[]{APITag.ACCOUNTS, APITag.PHASING}, "account");
    }

    @Override
    protected JSONStreamAware processRequest(HttpServletRequest req) throws HebeException {
        long accountId = ParameterParser.getAccountId(req, true);
        JSONObject response = new JSONObject();
        response.put("numberOfPhasedTransactions", PhasingPoll.getAccountPhasedTransactionCount(accountId));
        return response;
    }
}