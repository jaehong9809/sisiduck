package com.a702.finafanbe.core.batch.exception;

public class RetryTransactionException extends RuntimeException {
    public RetryTransactionException(String message) {
        super(message);
    }
}
