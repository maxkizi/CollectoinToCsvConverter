package org.eagleinvsys.test.converters.exception;

public class NotEqualsHeadersException extends RuntimeException {
    private static final String MESSAGE = "All headers in each record must be equals";

    public NotEqualsHeadersException() {
        super(MESSAGE);
    }
}
