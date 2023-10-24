package com.bbm.banking.exception;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String msg) {
        super(msg);
    }
}
