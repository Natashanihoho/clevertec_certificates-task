package ru.clevertec.ecl.api.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class EntityNotFoundException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final ErrorType errorType;

    public EntityNotFoundException(String message, HttpStatus httpStatus, ErrorType errorType) {
        super(message);
        this.httpStatus = httpStatus;
        this.errorType = errorType;
    }
}
