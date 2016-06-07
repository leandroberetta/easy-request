package ar.com.veicot.camel.components.easyrequest.http;

import ar.com.veicot.camel.components.easyrequest.EasyRequest;
import ar.com.veicot.camel.components.easyrequest.exceptions.InvalidHTTPMethodException;
import ar.com.veicot.camel.components.easyrequest.request.Request;

public class HTTPRequestFactory {

    public static HTTPRequest create(Request request) {
        if (request.getMethod().equals(HTTPRequest.GET))
            return new HTTPGetRequest(request);
        else if (request.getMethod().equals(HTTPRequest.PUT))
            return new HTTPPutRequest(request);
        else if (request.getMethod().equals(HTTPRequest.POST))
            return new HTTPPostRequest(request);
        else if (request.getMethod().equals(HTTPRequest.DELETE))
            return new HTTPDeleteRequest(request);

        throw new InvalidHTTPMethodException(EasyRequest.INVALID_HTTP_METHOD);
    }
}
