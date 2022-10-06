package ru.clevertec.ecl.api.exception;

import lombok.*;

@Data
@Builder
public class ResponseError {

    private final String errorCode;
    private final String message;
}
