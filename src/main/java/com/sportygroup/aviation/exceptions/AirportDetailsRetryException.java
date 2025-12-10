package com.sportygroup.aviation.exceptions;

public class AirportDetailsRetryException  extends RuntimeException {

    public AirportDetailsRetryException() {
        super();
    }

    public AirportDetailsRetryException(String message) {
        super(message);
    }

    public AirportDetailsRetryException(String message, Throwable cause) {
        super(message, cause);
    }
}
