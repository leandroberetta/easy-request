package ar.com.veicot.camel.components.easyrequest.http;

import ar.com.veicot.camel.components.easyrequest.request.Request;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

import java.io.IOException;

public class HTTPGetRequest implements HTTPRequest {

    private Request request;

    public HTTPGetRequest(Request request) {
        this.request = request;
    }

    @Override
    public HTTPResponse send(String url) {
        HttpClient client = new HttpClient();
        GetMethod method = new GetMethod(url);
        HTTPResponse response = null;

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
