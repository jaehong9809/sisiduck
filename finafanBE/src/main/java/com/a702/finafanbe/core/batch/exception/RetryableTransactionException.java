package com.a702.finafanbe.core.batch.exception;

public class RetryableTransactionException extends RuntimeException {
    public RetryableTransactionException(String message) {
        super(message);
    }

    public RetryableTransactionException(String message, Throwable cause) {
        super(message, cause);
    }

}
