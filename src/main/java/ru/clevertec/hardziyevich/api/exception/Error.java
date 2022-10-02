package ru.clevertec.hardziyevich.api.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@RequiredArgsConstructor
@Accessors(fluent = true)
@Getter
public enum Error {

    GIFT_CERTIFICATE("01", "Gift certificate"),
    TAG("02", "Tag");

    private final String code;
    private final String domainInformation;
}
