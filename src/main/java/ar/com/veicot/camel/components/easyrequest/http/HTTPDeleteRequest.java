package ar.com.veicot.camel.components.easyrequest.http;

import ar.com.veicot.camel.components.easyrequest.request.Request;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.DeleteMethod;

import java.io.IOException;

public class HTTPDeleteRequest implements HTTPRequest {

    private Request request;

    public HTTPDeleteRequest(Request request) {
        this.request = request;
    }

    @Override
    public HTTPResponse send(String url) {
        HttpClient client = new HttpClient();
        DeleteMethod method = new DeleteMethod(url);
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
