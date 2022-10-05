package ru.clevertec.ecl.api.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@RequiredArgsConstructor
@Accessors(fluent = true)
@Getter
public enum ErrorType {

    NOT_VALID_FIELD("00"),
    GIFT_CERTIFICATE("01"),
    TAG("02");

    private final String code;
}
