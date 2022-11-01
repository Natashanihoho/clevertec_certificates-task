package ru.clevertec.ecl.infrastructure.interceptor;

import com.google.gson.Gson;
import org.springframework.http.HttpEntity;
import org.springframework.web.servlet.HandlerInterceptor;
import ru.clevertec.ecl.infrastructure.filter.CachedBodyHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

public interface NodeInterceptor extends HandlerInterceptor {

    String URL = "http://localhost:";

    default Object jsonBody(HttpServletRequest request) {
        return new HttpEntity<>(new Gson().fromJson(((CachedBodyHttpServletRequest) request).getReader(), Object.class));
    }

    default <T> T jsonBody(HttpServletRequest request, Class<T> aClass) {
        return new Gson().fromJson(((CachedBodyHttpServletRequest) request).getReader(), aClass);
    }
}
