package com.a702.finafanbe.core.batch.exception;

public class SkipTransactionException extends RuntimeException {
    public SkipTransactionException(String message) {
        super(message);
    }
}
