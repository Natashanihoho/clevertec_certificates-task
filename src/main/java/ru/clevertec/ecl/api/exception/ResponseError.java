package ru.clevertec.ecl.api.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseError {

    private final String errorCode;
    private final String message;
}
