package com.kraken.uk.exception;

public class NotFoundException extends RuntimeException {
    private static final long serialVersionUID = -5236089248884090986L;

    public NotFoundException() {
    }

    public NotFoundException(final String message) {
        super(message);
    }
}
