package ru.clevertec.hardziyevich.api.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApplicationException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final Error error;

    public ApplicationException(String message, HttpStatus httpStatus, Error error) {
        super(message);
        this.httpStatus = httpStatus;
        this.error = error;
    }
}
