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

import hebe.Token;

import static hebe.http.JSONResponses.INCORRECT_WEBSITE;
import static hebe.http.JSONResponses.MISSING_TOKEN;
import static hebe.http.JSONResponses.MISSING_WEBSITE;

import javax.servlet.http.HttpServletRequest;

public final class DecodeToken extends APIServlet.APIRequestHandler {

    static final DecodeToken instance = new DecodeToken();

    private DecodeToken() {
        super(new APITag[] {APITag.TOKENS}, "website", "token");
    }

    @Override
    public JSONStreamAware processRequest(HttpServletRequest req) {

        String website = req.getParameter("website");
        String tokenString = req.getParameter("token");
        if (website == null) {
            return MISSING_WEBSITE;
        } else if (tokenString == null) {
            return MISSING_TOKEN;
        }

        try {

            Token token = Token.parseToken(tokenString, website.trim());

            return JSONData.token(token);

        } catch (RuntimeException e) {
            return INCORRECT_WEBSITE;
        }
    }

    @Override
    protected boolean allowRequiredBlockParameters() {
        return false;
    }

}
