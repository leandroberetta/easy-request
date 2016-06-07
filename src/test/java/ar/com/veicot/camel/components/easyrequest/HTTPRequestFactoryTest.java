package ar.com.veicot.camel.components.easyrequest;

import ar.com.veicot.camel.components.easyrequest.exceptions.InvalidHTTPMethodException;
import ar.com.veicot.camel.components.easyrequest.http.*;
import ar.com.veicot.camel.components.easyrequest.request.Request;
import ar.com.veicot.camel.components.easyrequest.request.RequestFactory;
import org.junit.Test;

import static org.junit.Assert.*;

public class HTTPRequestFactoryTest {

    private static final String SOURCE_PATH = "src/test/resources";

    @Test
    public void testCreateGetRequest() throws Exception {
        Request request = RequestFactory.create("request_get_test", SOURCE_PATH);

        HTTPRequest httpRequest = HTTPRequestFactory.create(request);

        assertEquals(httpRequest.getClass(), HTTPGetRequest.class);
    }

    @Test
    public void testCreatePutRequest() throws Exception {
        Request request = RequestFactory.create("request_put_test", SOURCE_PATH);

        HTTPRequest httpRequest = HTTPRequestFactory.create(request);

        assertEquals(httpRequest.getClass(), HTTPPutRequest.class);
    }

    @Test
    public void testCreatePostRequest() throws Exception {
        Request request = RequestFactory.create("request_post_test", SOURCE_PATH);

        HTTPRequest httpRequest = HTTPRequestFactory.create(request);

        assertEquals(httpRequest.getClass(), HTTPPostRequest.class);
    }

    @Test
    public void testCreateDeleteRequest() throws Exception {
        Request request = RequestFactory.create("request_delete_test", SOURCE_PATH);

        HTTPRequest httpRequest = HTTPRequestFactory.create(request);

        assertEquals(httpRequest.getClass(), HTTPDeleteRequest.class);
    }

    @Test(expected = InvalidHTTPMethodException.class)
    public void testFailCreatingRequestWithWrongMethod() throws Exception {
        Request request = new Request("", "", "FAKE");

        HTTPRequestFactory.create(request);
    }
}