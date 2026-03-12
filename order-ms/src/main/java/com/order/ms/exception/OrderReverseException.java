package com.order.ms.exception;

public class OrderReverseException extends RuntimeException {
    public OrderReverseException(String message, Throwable cause) {
        super(message, cause);
    }
}
