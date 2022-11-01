package ru.clevertec.ecl.infrastructure.interceptor.router;

import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;

public interface Router {

    void toResponse(HttpServletResponse response);

    static  <T> Router responseEntityRouter(ResponseEntity<T> responseEntity) {
        return new ResponseEntityRouter<>(responseEntity);
    }

}
