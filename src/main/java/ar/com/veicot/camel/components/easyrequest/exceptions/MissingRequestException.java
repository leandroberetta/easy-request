package ar.com.veicot.camel.components.easyrequest.exceptions;

/**
 * @author lberetta
 *
 */

public class MissingRequestException extends RuntimeException {

    public MissingRequestException(String msg) {
        super(msg);
    }
}
