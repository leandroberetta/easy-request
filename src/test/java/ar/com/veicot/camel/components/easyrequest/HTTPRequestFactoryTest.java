package ar.com.veicot.camel.components.easyrequest;

import ar.com.veicot.camel.components.easyrequest.exceptions.InvalidHTTPMethodException;
import ar.com.veicot.camel.components.easyrequest.http.HTTPMethodFactory;
import ar.com.veicot.camel.components.easyrequest.request.Request;
import ar.com.veicot.camel.components.easyrequest.request.RequestFactory;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.junit.Test;

import static org.junit.Assert.*;

public class HTTPRequestFactoryTest {

    private static final String SOURCE_PATH = "src/test/resources";
    private static final String URL = "/test";

    @Test
    public void testCreateGetMethod() throws Exception {
        Request request = RequestFactory.create("request_get_test", SOURCE_PATH);

        HttpMethodBase method = HTTPMethodFactory.create(request, URL);

        assertEquals(method.getClass(), GetMethod.class);
    }

    @Test
    public void testCreatePutRequest() throws Exception {
        Request request = RequestFactory.create("request_put_test", SOURCE_PATH);

        HttpMethodBase method = HTTPMethodFactory.create(request, URL);

        assertEquals(method.getClass(), PutMethod.class);
    }

    @Test
    public void testCreatePostRequest() throws Exception {
        Request request = RequestFactory.create("request_post_test", SOURCE_PATH);

        HttpMethodBase method = HTTPMethodFactory.create(request, URL);

        assertEquals(method.getClass(), PostMethod.class);
    }

    @Test
    public void testCreateDeleteRequest() throws Exception {
        Request request = RequestFactory.create("request_delete_test", SOURCE_PATH);

        HttpMethodBase method = HTTPMethodFactory.create(request, URL);

        assertEquals(method.getClass(), DeleteMethod.class);
    }

    @Test(expected = InvalidHTTPMethodException.class)
    public void testFailCreatingRequestWithWrongMethod() throws Exception {
        Request request = new Request("", "", "FAKE");

        HTTPMethodFactory.create(request, URL);
    }
}