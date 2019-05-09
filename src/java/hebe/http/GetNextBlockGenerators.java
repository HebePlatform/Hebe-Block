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

import hebe.Block;
import hebe.Constants;
import hebe.Hub;
import hebe.HEBE;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;

public final class GetNextBlockGenerators extends APIServlet.APIRequestHandler {

    static final GetNextBlockGenerators instance = new GetNextBlockGenerators();

    private GetNextBlockGenerators() {
        super(new APITag[] {APITag.FORGING});
    }

    @Override
    protected JSONStreamAware processRequest(HttpServletRequest req) {

        /* implement later, if needed
        Block curBlock;

        String block = req.getParameter("block");
        if (block == null) {
            curBlock = HEBE.getBlockchain().getLastBlock();
        } else {
            try {
                curBlock = HEBE.getBlockchain().getBlock(Convert.parseUnsignedLong(block));
                if (curBlock == null) {
                    return UNKNOWN_BLOCK;
                }
            } catch (RuntimeException e) {
                return INCORRECT_BLOCK;
            }
        }
        */

        Block curBlock = HEBE.getBlockchain().getLastBlock();
        if (curBlock.getHeight() < Constants.TRANSPARENT_FORGING_BLOCK_7) {
            return JSONResponses.FEATURE_NOT_AVAILABLE;
        }


        JSONObject response = new JSONObject();
        response.put("time", HEBE.getEpochTime());
        response.put("lastBlock", Long.toUnsignedString(curBlock.getId()));
        JSONArray hubs = new JSONArray();

        int limit;
        try {
            limit = Integer.parseInt(req.getParameter("limit"));
        } catch (RuntimeException e) {
            limit = Integer.MAX_VALUE;
        }

        Iterator<Hub.Hit> iterator = Hub.getHubHits(curBlock).iterator();
        while (iterator.hasNext() && hubs.size() < limit) {
            JSONObject hub = new JSONObject();
            Hub.Hit hit = iterator.next();
            hub.put("account", Long.toUnsignedString(hit.hub.getAccountId()));
            hub.put("minFeePerByteNQT", hit.hub.getMinFeePerByteNQT());
            hub.put("time", hit.hitTime);
            JSONArray uris = new JSONArray();
            uris.addAll(hit.hub.getUris());
            hub.put("uris", uris);
            hubs.add(hub);
        }
        
        response.put("hubs", hubs);
        return response;
    }

}
