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

import hebe.HEBE;
import hebe.util.Convert;

import static hebe.http.JSONResponses.INCORRECT_HEIGHT;
import static hebe.http.JSONResponses.MISSING_HEIGHT;

import javax.servlet.http.HttpServletRequest;

public final class GetBlockId extends APIServlet.APIRequestHandler {

    static final GetBlockId instance = new GetBlockId();

    private GetBlockId() {
        super(new APITag[] {APITag.BLOCKS}, "height");
    }

    @Override
    protected JSONStreamAware processRequest(HttpServletRequest req) {

        int height;
        try {
            String heightValue = Convert.emptyToNull(req.getParameter("height"));
            if (heightValue == null) {
                return MISSING_HEIGHT;
            }
            height = Integer.parseInt(heightValue);
        } catch (RuntimeException e) {
            return INCORRECT_HEIGHT;
        }

        try {
            JSONObject response = new JSONObject();
            response.put("block", Long.toUnsignedString(HEBE.getBlockchain().getBlockIdAtHeight(height)));
            return response;
        } catch (RuntimeException e) {
            return INCORRECT_HEIGHT;
        }

    }

}