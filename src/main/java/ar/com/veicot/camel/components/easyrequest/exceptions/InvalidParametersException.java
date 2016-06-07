package ar.com.veicot.camel.components.easyrequest.exceptions;

/**
 * @author lberetta
 *
 */

public class InvalidParametersException extends RuntimeException {

    public InvalidParametersException(String msg) {
        super(msg);
    }
}
