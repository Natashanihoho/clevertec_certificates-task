package ru.clevertec.ecl.api.exception;



import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.*;


@RestControllerAdvice
public class ExceptionCatcher {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ResponseError> entityNotFound(EntityNotFoundException exception) {
        return ResponseEntity.status(NOT_FOUND)
                .body(
                        ResponseError.builder()
                                .errorCode(NOT_FOUND.value() + exception.getErrorCode().code() + "1")
                                .message(exception.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseError> fieldNotValid(MethodArgumentNotValidException exception) {
        FieldError fieldError = exception.getBindingResult().getFieldError();
        return ResponseEntity.status(BAD_REQUEST)
                .body(
                        ResponseError.builder()
                                .message(fieldError.getField() + " " + fieldError.getDefaultMessage())
                                .errorCode(BAD_REQUEST.value() + ErrorCode.VALIDATION.code() + "1")
                                .build()
                );
    }
}
