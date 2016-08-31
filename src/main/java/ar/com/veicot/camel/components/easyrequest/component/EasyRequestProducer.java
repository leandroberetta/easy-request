package ar.com.veicot.camel.components.easyrequest.component;

import ar.com.veicot.camel.components.easyrequest.EasyRequest;
import ar.com.veicot.camel.components.easyrequest.http.HTTPRequestFactory;
import ar.com.veicot.camel.components.easyrequest.http.HTTPResponse;
import ar.com.veicot.camel.components.easyrequest.request.Request;
import ar.com.veicot.camel.components.easyrequest.request.RequestFactory;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultProducer;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Base64;

/**
 * @author lberetta
 *
 */

public class EasyRequestProducer extends DefaultProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(EasyRequestProducer.class);
    private EasyRequestEndpoint endpoint;

    public EasyRequestProducer(EasyRequestEndpoint endpoint) {
        super(endpoint);
        this.endpoint = endpoint;
    }

    public void process(Exchange exchange) throws Exception {
        Request request = RequestFactory.create(this.endpoint.getRequestId(),
                                                this.getRequestPath(exchange));

        // Uses the body as payload if true
        if (this.endpoint.isBodyPayload())
            request.setPayload((String) exchange.getIn().getBody());

        // Basic auth
        if (exchange.getIn().getHeader(EasyRequest.USERNAME) != null && exchange.getIn().getHeader(EasyRequest.PASSWORD) != null) {
            request.setUsername((String) exchange.getIn().getHeader(EasyRequest.USERNAME));
            request.setPassword((String) exchange.getIn().getHeader(EasyRequest.PASSWORD));

            request.setBasic(Base64.getEncoder().encodeToString(request.getUserNamePasswordForBasicEncoding().getBytes()));
        }

        StrSubstitutor substitutor = new StrSubstitutor(exchange.getIn().getHeaders());

        request.setPayload(substitutor.replace(request.getPayload()));

        // If host is null the HTTP call is bypassed and the request is set as the body
        if (this.endpoint.getHost() == null) {
            exchange.getOut().setHeader(EasyRequest.REQUEST_METHOD, request.getMethod());
            exchange.getOut().setHeader(EasyRequest.REQUEST_PATH, request.getPath());

            exchange.getOut().setBody(request.getPayload());
        } else {
            // HTTPS?
            request.setProtocol(EasyRequest.HTTP);

            HTTPResponse httpResponse = HTTPRequestFactory.send(request, request.getCompleteUrl(this.endpoint.getHost(), this.endpoint.getPort()));

            exchange.getOut().setHeader(EasyRequest.STATUS_CODE, httpResponse.getStatusCode());
            exchange.getOut().setBody(httpResponse.getPayload());
        }
    }

    private String getRequestPath(Exchange exchange) {
        return (String) exchange.getIn().getHeader(EasyRequest.REQUEST_PATH);
    }
}
