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

import hebe.HebeException;
import hebe.http.APIServlet.APIRequestHandler;
import hebe.peer.Peer;
import hebe.peer.Peers;

import static hebe.http.JSONResponses.MISSING_PEER;
import static hebe.http.JSONResponses.UNKNOWN_PEER;

import javax.servlet.http.HttpServletRequest;

public class BlacklistPeer extends APIRequestHandler {

    static final BlacklistPeer instance = new BlacklistPeer();
    
    private BlacklistPeer() {
        super(new APITag[] {APITag.NETWORK}, "peer");
    }

    @Override
    protected JSONStreamAware processRequest(HttpServletRequest request)
            throws HebeException {
        JSONObject response = new JSONObject();
        
        String peerAddress = request.getParameter("peer");
        if (peerAddress == null) {
            return MISSING_PEER;
        }
        Peer peer = Peers.findOrCreatePeer(peerAddress, true);
        if (peer == null) {
            return UNKNOWN_PEER;
        } else {
            Peers.addPeer(peer);
            peer.blacklist("Manual blacklist");
            response.put("done", true);
        }
        
        return response;
    }

    @Override
    protected final boolean requirePost() {
        return true;
    }

    @Override
    protected boolean requirePassword() {
        return true;
    }

    @Override
    protected boolean allowRequiredBlockParameters() {
        return false;
    }

    @Override
    protected boolean requireBlockchain() {
        return false;
    }

}
