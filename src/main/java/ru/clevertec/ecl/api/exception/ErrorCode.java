package ru.clevertec.ecl.api.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@RequiredArgsConstructor
@Accessors(fluent = true)
@Getter
public enum ErrorCode {

    VALIDATION(0),
    CERTIFICATE(1),
    TAG(2),
    USER(3),
    ORDER(4);

    private final int code;
}
