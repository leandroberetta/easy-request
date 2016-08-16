package ar.com.veicot.camel.components.easyrequest.http;

import ar.com.veicot.camel.components.easyrequest.EasyRequest;
import ar.com.veicot.camel.components.easyrequest.exceptions.InvalidHTTPMethodException;
import ar.com.veicot.camel.components.easyrequest.request.Request;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.methods.*;

import java.io.UnsupportedEncodingException;

public class HTTPMethodFactory {

    private static String GET = "GET";
    private static String POST = "POST";
    private static String PUT = "PUT";
    private static String DELETE = "DELETE";

    public static HttpMethodBase create(Request request, String url) throws UnsupportedEncodingException {

        if (request.getMethod().equals(GET))
            return new GetMethod(url);
        else if (request.getMethod().equals(PUT)) {
            PutMethod method = new PutMethod(url);
            method.setRequestEntity(new StringRequestEntity(request.getPayload(), request.getType(), "UTF-8"));

            return method;
        }
        else if (request.getMethod().equals(POST)) {
            PostMethod method = new PostMethod(url);
            method.setRequestEntity(new StringRequestEntity(request.getPayload(), request.getType(), "UTF-8"));

            return method;
        }
        else if (request.getMethod().equals(DELETE))
            return new DeleteMethod(url);

        throw new InvalidHTTPMethodException(EasyRequest.INVALID_HTTP_METHOD);
    }
}
