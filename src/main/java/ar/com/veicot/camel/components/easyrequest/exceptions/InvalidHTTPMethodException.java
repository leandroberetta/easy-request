package ar.com.veicot.camel.components.easyrequest.exceptions;

/**
 * @author lberetta
 *
 */

public class InvalidHTTPMethodException extends RuntimeException {

    public InvalidHTTPMethodException(String msg) {
        super(msg);
    }
}
