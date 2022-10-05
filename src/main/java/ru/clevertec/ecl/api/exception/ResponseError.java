package ru.clevertec.ecl.api.exception;

import lombok.Builder;

@Builder
public class ResponseError {

    private final String errorCode;
    private final String message;
}
