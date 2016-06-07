# easy-request

Camel component for send HTTP requests. Those requests are previously stored in files that encapsulate the information of the request achieving a cleaner and safer way to work with HTTP requests.

## Common usages

* Monitoring (Heartbeat)
* Test automation

## Usage

The information of a request is stored in a file with the following format:

    METHOD PATH

    PAYLOAD

### Example from the tests cases

    POST easyrequest/test

    { "test": "${toReplace}" }

In this kind of file the HTTP method, the HTTP service path and the payload has to be configured.

Then with the following Camel route (also from the tests cases) the requests is sent:

    from("direct:sendRequest")
        .setHeader("toReplace", "replaced);
        .setHeader(EasyRequest.REQUEST_PATH, "path/to/requests"))
        .to("easyrequest://host:port/requestId"))

With this route a request called **requestId** located in **path/to/requests** (if it's not indicated in the message
header, the default is in src/main/resources/requests) is sent to the target server.

Pay attention to the stored payload in the example above, a substitution can be made before the request is sent.

For more examples go to the easyrequest test cases.

