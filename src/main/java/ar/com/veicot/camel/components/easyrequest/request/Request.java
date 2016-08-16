package ar.com.veicot.camel.components.easyrequest.request;

import ar.com.veicot.camel.components.easyrequest.EasyRequest;

/**
 * @author lberetta
 *
 */

public class Request {
    private String payload;
    private String path;
    private String method;
    private String type;
    private String protocol;

    private String username;
    private String password;
    private String basic;

    public final static String APPLICATION_JSON = "application/json";
    public final static String APPLICATION_XML = "application/xml";

    public Request(String payload, String path, String method) {
        this.payload = payload.trim();
        this.path = path;
        this.method = method.toUpperCase();
        this.protocol = EasyRequest.HTTP;
        this.inspectRequestType();
    }

    private void inspectRequestType() {
        try {
            if (this.payload.charAt(0) == '<')
                this.type = APPLICATION_XML;
            else
                this.type = APPLICATION_JSON;

        } catch (StringIndexOutOfBoundsException e) {
            this.type = null;
        }
    }

    public String getType() {
        return this.type;
    }

    public boolean isValid() {
        return validatePath() && validateMethod();
    }

    private boolean validateMethod() {
        return !this.method.isEmpty() && this.method.matches("[A-Z]*");
    }

    private boolean validatePath() {
        return !this.method.isEmpty(); //&& this.method.matches("[A-Z]/*");
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getCompleteUrl(String url) {
        return this.protocol + url + "/" + this.path;
    }

    public String getCompleteUrl(String host, String port) {
        return this.getCompleteUrl(host + ":" + port);
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBasic() {
        return basic;
    }

    public void setBasic(String basic) {
        this.basic = basic;
    }

    public String getUserNamePasswordForBasicEncoding() {
        return this.username + ":" + this.password;
    }
}