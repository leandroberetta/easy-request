package ar.com.veicot.camel.components.easyrequest.component;

import ar.com.veicot.camel.components.easyrequest.EasyRequest;
import org.apache.camel.Endpoint;
import org.apache.camel.impl.UriEndpointComponent;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lberetta
 *
 */

public class EasyRequestComponent extends UriEndpointComponent {

    public EasyRequestComponent() {
        super(EasyRequestEndpoint.class);
    }

    protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
        Endpoint endpoint = new EasyRequestEndpoint(uri, this);

        setProperties(endpoint, parseUri(remaining));

        return endpoint;
    }

    private Map<String, Object> parseUri(String uri) {
        Map<String, Object> parameters = new HashMap<>();

        String pattern = "([a-zA-Z0-9{}$._-]+):([a-zA-Z0-9{}$._-]+)\\/([a-zA-Z0-9{}$._-]+)";

        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(uri);

        if (m.find()) {
            parameters.put(EasyRequest.HOST, m.group(1));
            parameters.put(EasyRequest.PORT, m.group(2));
            parameters.put(EasyRequest.REQUEST_ID, m.group(3));
        } else {
            parameters.put(EasyRequest.REQUEST_ID, uri);
        }

        return parameters;
    }
}
