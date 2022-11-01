package ru.clevertec.ecl.infrastructure.interceptor.router;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@RequiredArgsConstructor
public class ResponseEntityRouter<V> implements Router {

    private final ResponseEntity<V> responseEntity;

    @Override
    public void toResponse(HttpServletResponse response) {
        response.setStatus(responseEntity.getStatusCodeValue());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        try (PrintWriter writer = response.getWriter()) {
            writer.write(new Gson().toJson(responseEntity.getBody()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
