package ar.com.veicot.camel.components.easyrequest;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;

import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import java.util.HashMap;
import java.util.Properties;

public class ComponentTest extends CamelTestSupport {

    protected Properties properties;

    @EndpointInject(uri = "mock:end")
    protected MockEndpoint endEndpoint;

    @Produce(uri = "direct:sendRequest")
    protected ProducerTemplate sendRequestTemplate;

    @Produce(uri = "direct:sendRequestWithBodyPayload")
    protected ProducerTemplate sendRequestWithBodyPayloadTemplate;

    @Produce(uri = "direct:getPayload")
    protected ProducerTemplate getPayloadTemplate;

    public ComponentTest() {
        properties = new Properties();
    }

    @Override
    protected RoutesBuilder createRouteBuilder() throws Exception {

        RouteBuilder testRouteBuilder = new RouteBuilder() {
            @Override
            public void configure() throws Exception {

                restConfiguration()
                        .component("restlet")
                        .host("{{easyrequest.test.host}}")
                        .port("{{easyrequest.test.port}}")
                        .bindingMode(RestBindingMode.auto);

                rest("/easyrequest")
                        .post("test").to("direct:test")
                        .get("test").to("direct:test")
                        .put("test").to("direct:test")
                        .delete("test").to("direct:test");

                from("direct:test")
                        .setBody(simple("${body}"));

                from("direct:sendRequest")
                        .setHeader(EasyRequest.REQUEST_PATH, simple("{{easyrequest.request.sourcePath}}"))
                        .recipientList(simple("easyrequest://${header.requestUrl}/${header.requestId}"))
                        .to("mock:end");

                from("direct:getPayload")
                        .setHeader(EasyRequest.REQUEST_PATH, simple("{{easyrequest.request.sourcePath}}"))
                        .recipientList(simple("easyrequest:///${header.requestId}"))
                        .to("mock:end");

                from("direct:sendRequestWithBodyPayload")
                        .setHeader(EasyRequest.REQUEST_PATH, simple("{{easyrequest.request.sourcePath}}"))
                        .recipientList(simple("easyrequest://${header.requestUrl}/${header.requestId}?bodyPayload=true"))
                        .to("mock:end");
            }
        };

        return testRouteBuilder;
    }

    @Override
    protected Properties useOverridePropertiesWithPropertiesComponent() {

        properties.put("easyrequest.service.host", "localhost");
        properties.put("easyrequest.service.port", "5555");

        properties.put("easyrequest.request.sourcePath", "src/test/resources");

        properties.put("easyrequest.test.host", "localhost");
        properties.put("easyrequest.test.port", "5556");

        return properties;
    }

    @Test
    public void testPostRequest() throws Exception {
        String expectedBody = "{ \"test\" : \"payloadFromFileRequest\" }";

        endEndpoint.expectedBodiesReceived(expectedBody);
        endEndpoint.expectedHeaderReceived(EasyRequest.STATUS_CODE, "200");

        HashMap<String, Object> headers = new HashMap<>();

        headers.put("requestUrl", String.format("%s:%s",
                properties.getProperty("easyrequest.test.host"),
                properties.getProperty("easyrequest.test.port")));
        headers.put("requestId", "request_post_test");

        sendRequestTemplate.sendBodyAndHeaders(null, headers);

        endEndpoint.assertIsSatisfied();
    }

    @Test
    public void testPostRequestWithBodyAsPayload() throws Exception {
        String expectedBody = "{ \"test\" : \"payloadFromBody\" }";

        endEndpoint.expectedBodiesReceived(expectedBody);
        endEndpoint.expectedHeaderReceived(EasyRequest.STATUS_CODE, "200");

        HashMap<String, Object> headers = new HashMap<>();

        headers.put("requestUrl", String.format("%s:%s",
                properties.getProperty("easyrequest.test.host"),
                properties.getProperty("easyrequest.test.port")));
        headers.put("requestId", "request_post_test");

        sendRequestWithBodyPayloadTemplate.sendBodyAndHeaders("{ \"test\" : \"payloadFromBody\" }", headers);

        endEndpoint.assertIsSatisfied();
    }

    @Test
    public void testGetRequest() throws Exception {
        endEndpoint.expectedHeaderReceived(EasyRequest.STATUS_CODE, "200");

        HashMap<String, Object> headers = new HashMap<>();

        headers.put("requestUrl", String.format("%s:%s",
                properties.getProperty("easyrequest.test.host"),
                properties.getProperty("easyrequest.test.port")));
        headers.put("requestId", "request_get_test");

        sendRequestTemplate.sendBodyAndHeaders(null, headers);

        endEndpoint.assertIsSatisfied();
    }

    @Test
    public void testPutRequest() throws Exception {
        String expectedBody = "{ \"test\" : \"test\" }";

        endEndpoint.expectedBodiesReceived(expectedBody);
        endEndpoint.expectedHeaderReceived(EasyRequest.STATUS_CODE, "200");

        HashMap<String, Object> headers = new HashMap<>();

        headers.put("requestUrl", String.format("%s:%s",
                properties.getProperty("easyrequest.test.host"),
                properties.getProperty("easyrequest.test.port")));
        headers.put("requestId", "request_put_test");

        sendRequestTemplate.sendBodyAndHeaders(null, headers);

        endEndpoint.assertIsSatisfied();
    }

    @Test
    public void testDeleteRequest() throws Exception {
        endEndpoint.expectedHeaderReceived(EasyRequest.STATUS_CODE, "200");

        HashMap<String, Object> headers = new HashMap<>();

        headers.put("requestUrl", String.format("%s:%s",
                properties.getProperty("easyrequest.test.host"),
                properties.getProperty("easyrequest.test.port")));
        headers.put("requestId", "request_delete_test");

        sendRequestTemplate.sendBodyAndHeaders(null, headers);

        endEndpoint.assertIsSatisfied();
    }

    @Test
    public void testRequestWithoutHostPort() throws Exception {
        String expectedBody = "{ \"test\" : \"payloadFromFileRequest\" }";

        endEndpoint.expectedBodiesReceived(expectedBody);
        endEndpoint.expectedHeaderReceived(EasyRequest.REQUEST_PATH, "easyrequest/test");
        endEndpoint.expectedHeaderReceived(EasyRequest.REQUEST_METHOD, "POST");

        HashMap<String, Object> headers = new HashMap<>();

        headers.put("requestId", "request_post_test");

        getPayloadTemplate.sendBodyAndHeaders(expectedBody, headers);

        endEndpoint.assertIsSatisfied();
    }

    @Test
    public void testRequestWithSubstitutions() throws Exception {
        String expectedBody = "{ \"test\": \"replaced\" }";

        endEndpoint.expectedBodiesReceived(expectedBody);
        endEndpoint.expectedHeaderReceived(EasyRequest.STATUS_CODE, "200");

        HashMap<String, Object> headers = new HashMap<>();

        headers.put("requestUrl", String.format("%s:%s",
                properties.getProperty("easyrequest.test.host"),
                properties.getProperty("easyrequest.test.port")));
        headers.put("requestId", "request_substitution_test");
        headers.put("toReplace", "replaced");

        sendRequestTemplate.sendBodyAndHeaders(null, headers);

        endEndpoint.assertIsSatisfied();
    }
}
