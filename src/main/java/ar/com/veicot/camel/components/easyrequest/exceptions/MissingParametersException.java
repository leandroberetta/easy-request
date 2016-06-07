package ar.com.veicot.camel.components.easyrequest.exceptions;

/**
 * @author lberetta
 *
 */

public class MissingParametersException extends RuntimeException {

    public MissingParametersException(String msg) {
        super(msg);
    }
}
