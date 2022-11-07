package ru.clevertec.ecl.infrastructure.interceptor.order;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.HandlerInterceptor;
import com.google.gson.Gson;

public interface OrderInterceptor extends HandlerInterceptor {

    default <T> void routeResponseEntityToResponse(ResponseEntity<T> responseEntity, HttpServletResponse response) {
        response.setStatus(responseEntity.getStatusCodeValue());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        try (PrintWriter writer = response.getWriter()) {
            writer.write(new Gson().toJson(responseEntity.getBody()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
