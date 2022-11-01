package ru.clevertec.ecl.infrastructure.node;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor
public class IdHandler {

    private final Node node;

    public boolean isCurrentNode(HttpServletRequest httpServletRequest) {
        return findIdInUrl(httpServletRequest)
                .map(id -> ((id - 1) % node.getCapacity()) + 1 == node.getNumber())
                .orElse(false);
    }

    public String getOrderUri(HttpServletRequest httpServletRequest) {
        if (isIdExist(httpServletRequest)) {
            return replaceIdInUrl(httpServletRequest);
        }
        return httpServletRequest.getRequestURI();
    }

    private String replaceIdInUrl(HttpServletRequest httpServletRequest) {
        return findIdInUrl(httpServletRequest)
                .map(id -> httpServletRequest.getRequestURI()
                        .replace(String.valueOf(id), String.valueOf(id / node.getCapacity() + 1))
                ).orElseThrow(RuntimeException::new);
    }

    private boolean isIdExist(HttpServletRequest httpServletRequest) {
        return findIdInUrl(httpServletRequest).isPresent();
    }

    private Optional<Integer> findIdInUrl(HttpServletRequest httpServletRequest) {
        return Arrays.stream(httpServletRequest.getRequestURI().split("/"))
                .filter(StringUtils::isNumeric)
                .map(Integer::valueOf)
                .findFirst();
    }
}
