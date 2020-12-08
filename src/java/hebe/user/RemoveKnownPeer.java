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
package hebe.user;

import org.json.simple.JSONStreamAware;

import hebe.peer.Peer;

import javax.servlet.http.HttpServletRequest;

import static hebe.user.JSONResponses.LOCAL_USERS_ONLY;

import java.io.IOException;
import java.net.InetAddress;

public final class RemoveKnownPeer extends UserServlet.UserRequestHandler {

    static final RemoveKnownPeer instance = new RemoveKnownPeer();

    private RemoveKnownPeer() {}

    @Override
    JSONStreamAware processRequest(HttpServletRequest req, User user) throws IOException {
        if (Users.allowedUserHosts == null && ! InetAddress.getByName(req.getRemoteAddr()).isLoopbackAddress()) {
            return LOCAL_USERS_ONLY;
        } else {
            int index = Integer.parseInt(req.getParameter("peer"));
            Peer peer = Users.getPeer(index);
            if (peer != null) {
                peer.remove();
            }
        }
        return null;
    }
}
