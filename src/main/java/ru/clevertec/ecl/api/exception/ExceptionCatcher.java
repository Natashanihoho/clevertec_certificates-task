package ru.clevertec.ecl.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static ru.clevertec.ecl.api.exception.ErrorType.NOT_VALID_FIELD;

@RestControllerAdvice
public class ExceptionCatcher {

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<ResponseError> entityNotFound(EntityNotFoundException exception) {
        return ResponseEntity.status(exception.getHttpStatus())
                .body(
                        ResponseError.builder()
                                .message(exception.getMessage())
                                .errorCode(exception.getHttpStatus().value() + exception.getErrorType().code())
                                .build()
                );
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ResponseError> fieldNotValid(MethodArgumentNotValidException exception) {
        FieldError fieldError = exception.getBindingResult().getFieldError();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        ResponseError.builder()
                                .message(fieldError.getField() + " " + fieldError.getDefaultMessage())
                                .errorCode(HttpStatus.BAD_REQUEST.value() + NOT_VALID_FIELD.code())
                                .build()
                );
    }
}
