package ar.com.veicot.camel.components.easyrequest.http;

public class HTTPResponse {

    private String payload;
    private Integer statusCode;

    public HTTPResponse(String payload, Integer statusCode) {
        this.payload = payload;
        this.statusCode = statusCode;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public String getPayload() {
        return payload;
    }

}
