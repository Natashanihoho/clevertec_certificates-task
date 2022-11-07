package ru.clevertec.ecl.infrastructure.interceptor.order.url;

import static ru.clevertec.ecl.infrastructure.interceptor.order.url.Attribute.ID;
import static ru.clevertec.ecl.infrastructure.interceptor.order.url.Attribute.PAGE;
import static ru.clevertec.ecl.infrastructure.interceptor.order.url.Attribute.SIZE;
import static ru.clevertec.ecl.infrastructure.interceptor.order.url.Attribute.SORT;

import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.HandlerMapping;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UrlPathParser {

    private final Map<String, String> pathVariables;
    private final HttpServletRequest request;

    public UrlPathParser(HttpServletRequest request) {
        this((Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE), request);
    }

    public boolean isAttribute(Attribute attribute) {
        return pathVariables.containsKey(attribute.toString());
    }

    public int getIdAttribute() {
        return Integer.parseInt(pathVariables.get(ID.toString()));
    }

    public boolean arePaginationParameterExisted() {
        return isParameterExisted(PAGE) || isParameterExisted(SORT) || isParameterExisted(SIZE);
    }

    public int getParameter(Attribute attribute) {
        return Integer.parseInt(request.getParameter(attribute.toString()));
    }

    private boolean isParameterExisted(Attribute attribute) {
        return Optional.ofNullable(request.getParameter(attribute.toString())).isPresent();
    }
}
