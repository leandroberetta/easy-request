package ar.com.veicot.camel.components.easyrequest.http;

import ar.com.veicot.camel.components.easyrequest.request.Request;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class HTTPPutRequest implements HTTPRequest {

    private Request request;

    public HTTPPutRequest(Request request) {
        this.request = request;
    }

    @Override
    public HTTPResponse send(String url) {
        HttpClient client = new HttpClient();
        PutMethod method = new PutMethod(url);
        HTTPResponse response = null;

        method.setRequestHeader("Content-Type", request.getType());
        try {
            method.setRequestEntity(new StringRequestEntity(request.getPayload(), request.getType(), null));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

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