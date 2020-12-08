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

import hebe.Poll;
import hebe.Vote;
import hebe.VoteWeighting;
import hebe.HEBE;
import hebe.HebeException;
import hebe.util.JSON;

import javax.servlet.http.HttpServletRequest;

public class GetPollVote extends APIServlet.APIRequestHandler  {
    static final GetPollVote instance = new GetPollVote();

    private GetPollVote() {
        super(new APITag[] {APITag.VS}, "poll", "account", "includeWeights");
    }

    @Override
    protected JSONStreamAware processRequest(HttpServletRequest req) throws HebeException {
        Poll poll = ParameterParser.getPoll(req);
        long accountId = ParameterParser.getAccountId(req, true);
        boolean includeWeights = "true".equalsIgnoreCase(req.getParameter("includeWeights"));
        Vote vote = Vote.getVote(poll.getId(), accountId);
        if (vote != null) {
            int countHeight;
            JSONData.VoteWeighter weighter = null;
            if (includeWeights && (countHeight = Math.min(poll.getFinishHeight(), HEBE.getBlockchain().getHeight()))
                    >= HEBE.getBlockchainProcessor().getMinRollbackHeight()) {
                VoteWeighting voteWeighting = poll.getVoteWeighting();
                VoteWeighting.VotingModel votingModel = voteWeighting.getVotingModel();
                weighter = voterId -> votingModel.calcWeight(voteWeighting, voterId, countHeight);
            }
            return JSONData.vote(vote, weighter);
        }
        return JSON.emptyJSON;
    }
}
