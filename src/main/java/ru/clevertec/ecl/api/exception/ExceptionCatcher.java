package ru.clevertec.ecl.api.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;


@RestControllerAdvice
public class ExceptionCatcher {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ResponseError> entityNotFound(EntityNotFoundException exception) {
        return ResponseEntity.status(NOT_FOUND)
                .body(
                        ResponseError.builder()
                                .errorCode(NOT_FOUND.value() + "0" + exception.getErrorCode().code())
                                .message(exception.getMessage())
                                .build()
                );
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public ResponseEntity<ResponseError> fieldNotValid(Exception exception) {
        return ResponseEntity.status(BAD_REQUEST)
                .body(
                        ResponseError.builder()
                                .message(exception.getMessage())
                                .errorCode(BAD_REQUEST.value() + "0" + ErrorCode.VALIDATION.code())
                                .build()
                );
    }
}
