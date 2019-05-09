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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONStreamAware;

import hebe.Account;
import hebe.HebeException;
import hebe.db.DbIterator;

import javax.servlet.http.HttpServletRequest;

public final class GetCurrencyAccounts extends APIServlet.APIRequestHandler {

    static final GetCurrencyAccounts instance = new GetCurrencyAccounts();

    private GetCurrencyAccounts() {
        super(new APITag[] {APITag.MS}, "currency", "height", "firstIndex", "lastIndex");
    }

    @Override
    protected JSONStreamAware processRequest(HttpServletRequest req) throws HebeException {

        long currencyId = ParameterParser.getUnsignedLong(req, "currency", true);
        int firstIndex = ParameterParser.getFirstIndex(req);
        int lastIndex = ParameterParser.getLastIndex(req);
        int height = ParameterParser.getHeight(req);

        JSONArray accountCurrencies = new JSONArray();
        try (DbIterator<Account.AccountCurrency> iterator = Account.getCurrencyAccounts(currencyId, height, firstIndex, lastIndex)) {
            while (iterator.hasNext()) {
                Account.AccountCurrency accountCurrency = iterator.next();
                accountCurrencies.add(JSONData.accountCurrency(accountCurrency, true, false));
            }
        }

        JSONObject response = new JSONObject();
        response.put("accountCurrencies", accountCurrencies);
        return response;

    }

}
