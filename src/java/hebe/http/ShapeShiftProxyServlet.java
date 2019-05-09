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

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.api.Response;
import org.eclipse.jetty.proxy.AsyncMiddleManServlet;
import org.json.simple.JSONObject;

import hebe.util.JSON;
import hebe.util.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

public final class ShapeShiftProxyServlet extends AsyncMiddleManServlet {

    static final String SHAPESHIFT_TARGET = "/shapeshift";

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            super.service(new ClientRequestWrapper(request), response);
        } catch(Exception e) {
            JSONObject errorJson = new JSONObject();
            errorJson.put("errorDescription", e.getMessage());
            try {
                try (Writer writer = response.getWriter()) {
                    JSON.writeJSONString(JSON.prepare(errorJson), writer);
                }
            } catch(IOException ioe) {
                Logger.logInfoMessage("Failed to write response to client", ioe);
            }
        }
    }

    @Override
    protected HttpClient newHttpClient() {
        return HttpClientFactory.newHttpClient();
    }

    @Override
    protected String rewriteTarget(HttpServletRequest clientRequest) {
        return "https://shapeshift.io" + clientRequest.getRequestURI();
    }

    @Override
    protected void onClientRequestFailure(HttpServletRequest clientRequest, Request proxyRequest,
                                          HttpServletResponse proxyResponse, Throwable failure) {
        super.onClientRequestFailure(clientRequest, proxyRequest, proxyResponse, failure);
    }

    @Override
    protected Response.Listener newProxyResponseListener(HttpServletRequest request, HttpServletResponse response) {
        return new APIProxyResponseListener(request, response);
    }

    private class APIProxyResponseListener extends ProxyResponseListener {

        APIProxyResponseListener(HttpServletRequest request, HttpServletResponse response) {
            super(request, response);
        }

        @Override
        public void onFailure(Response response, Throwable failure) {
            super.onFailure(response, failure);
            Logger.logErrorMessage("shape shift proxy failed", failure);
        }
    }

    private static class ClientRequestWrapper extends HttpServletRequestWrapper {

        private final HttpServletRequest request;

        ClientRequestWrapper(HttpServletRequest request) {
            super(request);
            this.request = request;
        }

        @Override
        public String getRequestURI() {
            String uri = request.getRequestURI();
            if (uri != null && uri.startsWith(SHAPESHIFT_TARGET)) {
                uri = uri.replaceFirst(SHAPESHIFT_TARGET, "");
            }
            return uri;
        }
    }
}