package ar.com.veicot.camel.components.easyrequest.request;

import ar.com.veicot.camel.components.easyrequest.EasyRequest;
import ar.com.veicot.camel.components.easyrequest.exceptions.InvalidParametersException;
import ar.com.veicot.camel.components.easyrequest.exceptions.MissingParametersException;
import ar.com.veicot.camel.components.easyrequest.exceptions.MissingRequestException;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lberetta
 *
 */

public class RequestFactory {
    public static Request create(String requestId, String requestPath) {
        ArrayList<String> requestLines = (ArrayList<String>) getRequestLinesFromFile(requestId, requestPath);

        String header = requestLines.remove(0);

        String method = getMethodFromHeader(header);
        String path = getPathFromHeader(header);
        String body = getBodyFromLines(requestLines);

        Request request = new Request(body, path, method);

        if (!request.isValid())
            throw new InvalidParametersException(EasyRequest.INVALID_PARAMETERS);

        return request;
    }

    private static List<String> getRequestLinesFromFile(String requestName, String requestPath) {
        try {
            if (requestPath != null)
                return Files.readAllLines(Paths.get(requestPath + "/" + requestName), Charset.forName("ISO-8859-1"));
            else {
                return Files.readAllLines(Paths.get("src/main/resources/requests/" + requestName), Charset.forName("ISO-8859-1"));
            }

        } catch (IOException e) {
            throw new MissingRequestException(EasyRequest.MISSING_REQUEST);
        }
    }

    private static String getMethodFromHeader(String header) {
        try {
            return header.split(" ")[0];
        } catch (IndexOutOfBoundsException e) {
            throw new MissingParametersException(EasyRequest.MISSING_PARAMETERS);
        }
    }

    private static String getPathFromHeader(String header) {
        try {
            return header.split(" ")[1];
        } catch (IndexOutOfBoundsException e) {
            throw new MissingParametersException(EasyRequest.MISSING_PARAMETERS);
        }
    }

    private static String getBodyFromLines(ArrayList<String> bodyLines) {
        StringBuilder sb = new StringBuilder();

        for (String line:bodyLines)
            sb.append(line);

        return sb.toString();
    }
}
