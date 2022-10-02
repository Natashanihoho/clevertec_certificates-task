package ru.clevertec.hardziyevich.api.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@RequiredArgsConstructor
@Accessors(fluent = true)
@Getter
public enum ExceptionMessage {

    NOT_EXIST("%s does not exist with %s");

    private final String format;

}
