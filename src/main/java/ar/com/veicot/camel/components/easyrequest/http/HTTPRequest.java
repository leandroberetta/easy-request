package ar.com.veicot.camel.components.easyrequest.http;


public interface HTTPRequest {

    String GET = "GET";
    String POST = "POST";
    String PUT = "PUT";
    String DELETE = "DELETE";

    HTTPResponse send(String url);
}
