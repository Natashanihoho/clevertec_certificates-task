package ru.clevertec.hardziyevich.api.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionCatcher {

    @ExceptionHandler({ApplicationException.class})
    public ResponseEntity<ResponseError> entityNotFound(ApplicationException exception) {
        return ResponseEntity.status(exception.getHttpStatus())
                .body(
                        ResponseError.builder()
                                .message(exception.getMessage())
                                .errorCode(exception.getHttpStatus().value() + exception.getError().code())
                                .build()
                );
    }

}
