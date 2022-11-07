package ru.clevertec.ecl.infrastructure.interceptor.order.url;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Attribute {

    ID,
    PAGE,
    SIZE,
    SORT;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
