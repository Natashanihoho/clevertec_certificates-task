package ru.clevertec.hardziyevich.api.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseError {

    private final String message;
    private final String errorCode;
}
