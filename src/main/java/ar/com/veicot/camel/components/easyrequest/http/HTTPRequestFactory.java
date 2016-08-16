package ar.com.veicot.camel.components.easyrequest.http;

import ar.com.veicot.camel.components.easyrequest.EasyRequest;
import ar.com.veicot.camel.components.easyrequest.exceptions.InvalidHTTPMethodException;
import ar.com.veicot.camel.components.easyrequest.request.Request;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethodBase;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class HTTPRequestFactory {

    public static HTTPResponse send(Request request, String url) {

        HttpClient client = new HttpClient();
        HTTPResponse response = null;

        HttpMethodBase method = null;

        try {
            method = HTTPMethodFactory.create(request, url);
        } catch (UnsupportedEncodingException e) {
            throw new InvalidHTTPMethodException(EasyRequest.INVALID_HTTP_METHOD);
        }

        if (request.getBasic() != null) {
            method.setRequestHeader("Authorization", "Basic " + request.getBasic());
        }

        method.setRequestHeader("Content-Type", request.getType());

        try {
            int statusCode = client.executeMethod(method);

            byte[] responseBody = method.getResponseBody();

            response = new HTTPResponse(new String(responseBody), statusCode);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            method.releaseConnection();
        }

        return response;
    }
}
