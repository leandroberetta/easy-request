package ar.com.veicot.camel.components.easyrequest.http;

import ar.com.veicot.camel.components.easyrequest.request.Request;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by lberetta on 6/3/16.
 */
public class HTTPPostRequest implements HTTPRequest {

    private Request request;

    public HTTPPostRequest(Request request) {
        this.request = request;
    }

    @Override
    public HTTPResponse send(String url) {
        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(url);
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

