package ar.com.veicot.camel.components.easyrequest.component;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;
import org.apache.camel.spi.UriEndpoint;
import org.apache.camel.spi.UriParam;

/**
 * @author lberetta
 *
 */

@UriEndpoint(scheme = "easyrequest",
             title = "EasyRequest",
             syntax = "easyrequest:uri",
             producerOnly = true)
public class EasyRequestEndpoint extends DefaultEndpoint {

//    @UriPath
//    private String uri;

    private String host;
    private String port;
    private String requestId;

    @UriParam(defaultValue = "false")
    private String bodyPayload = "false";

    public EasyRequestEndpoint() {
    }

    public EasyRequestEndpoint(String uri, EasyRequestComponent component) {
        super(uri, component);
    }

    public Producer createProducer() throws Exception {
        return new EasyRequestProducer(this);
    }

    public Consumer createConsumer(Processor processor) throws Exception {
        throw new UnsupportedOperationException();
    }

    public boolean isSingleton() {
        return false;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getBodyPayload() {
        return bodyPayload;
    }

    public void setBodyPayload(String bodyPayload) {
        this.bodyPayload = bodyPayload;
    }

    public boolean isBodyPayload() {
        if (this.getBodyPayload() != null && this.getBodyPayload().equals("false"))
            return false;
        return true;
    }
}
