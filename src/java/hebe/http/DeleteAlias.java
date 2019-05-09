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
import hebe.Alias;
import hebe.Attachment;
import hebe.HebeException;

import static hebe.http.JSONResponses.INCORRECT_ALIAS_OWNER;

import javax.servlet.http.HttpServletRequest;


public final class DeleteAlias extends CreateTransaction {

    static final DeleteAlias instance = new DeleteAlias();

    private DeleteAlias() {
        super(new APITag[] {APITag.ALIASES, APITag.CREATE_TRANSACTION}, "alias", "aliasName");
    }

    @Override
    protected JSONStreamAware processRequest(final HttpServletRequest req) throws HebeException {
        final Alias alias = ParameterParser.getAlias(req);
        final Account owner = ParameterParser.getSenderAccount(req);

        if (alias.getAccountId() != owner.getId()) {
            return INCORRECT_ALIAS_OWNER;
        }

        final Attachment attachment = new Attachment.MessagingAliasDelete(alias.getAliasName());
        return createTransaction(req, owner, attachment);
    }
}
