package ru.clevertec.hardziyevich.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.clevertec.hardziyevich.infrastructure.ApplicationException;

@ControllerAdvice
public class ExceptionCatcher {

    @ExceptionHandler({ApplicationException.class})
    public ResponseEntity<String> entityNotFound(ApplicationException exception) {
        return ResponseEntity.status(exception.getHttpStatus())
                .body(exception.getMessage());
    }
}
