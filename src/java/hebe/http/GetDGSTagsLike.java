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

import hebe.DigitalGoodsStore;
import hebe.HebeException;
import hebe.db.DbIterator;
import hebe.util.Convert;

import javax.servlet.http.HttpServletRequest;

public final class GetDGSTagsLike extends APIServlet.APIRequestHandler {

    static final GetDGSTagsLike instance = new GetDGSTagsLike();

    private GetDGSTagsLike() {
        super(new APITag[] {APITag.DGS, APITag.SEARCH}, "tagPrefix", "inStockOnly", "firstIndex", "lastIndex");
    }

    @Override
    protected JSONStreamAware processRequest(HttpServletRequest req) throws HebeException {
        int firstIndex = ParameterParser.getFirstIndex(req);
        int lastIndex = ParameterParser.getLastIndex(req);
        final boolean inStockOnly = "true".equalsIgnoreCase(req.getParameter("inStockOnly"));
        String prefix = Convert.emptyToNull(req.getParameter("tagPrefix"));
        if (prefix == null) {
            return JSONResponses.missing("tagPrefix");
        }
        if (prefix.length() < 2) {
            return JSONResponses.incorrect("tagPrefix", "tagPrefix must be at least 2 characters long");
        }

        JSONObject response = new JSONObject();
        JSONArray tagsJSON = new JSONArray();
        response.put("tags", tagsJSON);
        try (DbIterator<DigitalGoodsStore.Tag> tags = DigitalGoodsStore.Tag.getTagsLike(prefix, inStockOnly, firstIndex, lastIndex)) {
            while (tags.hasNext()) {
                tagsJSON.add(JSONData.tag(tags.next()));
            }
        }
        return response;
    }

}
