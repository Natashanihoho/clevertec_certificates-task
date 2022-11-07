package ru.clevertec.ecl.infrastructure.interceptor.order.url;

import static ru.clevertec.ecl.infrastructure.interceptor.order.url.Attribute.PAGE;
import static ru.clevertec.ecl.infrastructure.interceptor.order.url.Attribute.SIZE;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PaginationSerializer {

    private final UrlPathParser urlPathParser;

    public int size() {
        return urlPathParser.getParameter(SIZE);
    }

    public int page() {
        return urlPathParser.getParameter(PAGE);
    }

    public int minPaginationValue() {
        return size() * page();
    }

    public int maxPaginationValue() {
        return size() * (page() + 1);
    }

    public String generateEndPointForPagination() {
        return String.format("/api/v1/orders/id?min=%d&max=%d", minPaginationValue(), maxPaginationValue());
    }
}
