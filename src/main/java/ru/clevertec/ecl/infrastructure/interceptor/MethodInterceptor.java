package ru.clevertec.ecl.infrastructure.interceptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.EnumMap;

@Log4j2
@RequiredArgsConstructor
public class MethodInterceptor implements HandlerInterceptor {

    private final EnumMap<HttpMethod, HandlerInterceptor> router;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpMethod httpMethod = HttpMethod.resolve(request.getMethod());
        if (router.containsKey(httpMethod)) {
            log.info("HTTP method " + httpMethod);
            return router.get(httpMethod).preHandle(request, response, handler);
        }
        return true;
    }
}
