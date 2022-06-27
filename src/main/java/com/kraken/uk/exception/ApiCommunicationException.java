package com.kraken.uk.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ApiCommunicationException extends RuntimeException {
    private static final long serialVersionUID = 6138887933983328059L;

    public ApiCommunicationException() {
        super("Error calling the api");
    }
    public ApiCommunicationException(final String message) {
        super(message);
    }
}
