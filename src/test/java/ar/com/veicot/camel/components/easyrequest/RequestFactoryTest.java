package ar.com.veicot.camel.components.easyrequest;

import ar.com.veicot.camel.components.easyrequest.exceptions.MissingRequestException;
import ar.com.veicot.camel.components.easyrequest.exceptions.InvalidParametersException;
import ar.com.veicot.camel.components.easyrequest.exceptions.MissingParametersException;
import ar.com.veicot.camel.components.easyrequest.request.Request;
import ar.com.veicot.camel.components.easyrequest.request.RequestFactory;
import org.junit.Test;

import static org.junit.Assert.*;

public class RequestFactoryTest {

    @Test
    public void testCreateSuccessfulRequest() {
        Request req = RequestFactory.create("request_test", "src/test/resources");

        assertEquals("POST", req.getMethod());
        assertEquals("test", req.getPath());
        assertEquals(Request.APPLICATION_JSON, req.getType());
    }

    @Test(expected = MissingRequestException.class)
    public void testFailCreatingNonexistentRequest() {
        Request req = RequestFactory.create("request_test", "fake_path");
    }

    @Test
    public void testCreateSuccessfulJSONRequest() {
        Request req = RequestFactory.create("request_json_test", "src/test/resources");

        assertEquals(Request.APPLICATION_JSON, req.getType());
    }

    @Test
    public void testCreateSuccessfulXMLRequest() {
        Request req = RequestFactory.create("request_xml_test", "src/test/resources");

        assertEquals(Request.APPLICATION_XML, req.getType());
    }

    @Test
    public void testCreatingRequestWithEmptyBody() {
        Request req = RequestFactory.create("request_empty_body_test", "src/test/resources");

        assertEquals(null, req.getType());
    }

    @Test(expected = MissingParametersException.class)
    public void testFailCreatingRequestWithMissingParametersInHeader() {
        Request req = RequestFactory.create("request_missing_parameters_test", "src/test/resources");
    }

    @Test(expected = InvalidParametersException.class)
    public void testFailCreatingRequestWithInvalidParametersInHeader() {
        Request req = RequestFactory.create("request_invalid_parameters_test", "src/test/resources");
    }

    @Test(expected = InvalidParametersException.class)
    public void testFailCreatingRequestWithoutHeader() {
        Request req = RequestFactory.create("request_without_header_test", "src/test/resources");
    }
}