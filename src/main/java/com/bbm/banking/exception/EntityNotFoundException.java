package com.bbm.banking.exception;

public class EntityNotFoundException extends BadRequestException{
    public EntityNotFoundException(String msg) {
        super(msg);
    }
}
