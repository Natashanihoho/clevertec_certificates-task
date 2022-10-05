package ru.clevertec.ecl.api.exception;

import lombok.Getter;

@Getter
public class EntityNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;

    public EntityNotFoundException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
