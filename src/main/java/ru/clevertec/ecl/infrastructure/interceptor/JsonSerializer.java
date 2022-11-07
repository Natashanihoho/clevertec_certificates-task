package ru.clevertec.ecl.infrastructure.interceptor;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ru.clevertec.ecl.infrastructure.filter.CachedBodyHttpServletRequest;

@Component
@Log4j2
@RequiredArgsConstructor
public class JsonSerializer {

    private final ObjectMapper objectMapper;

    public <T> T jsonObject(HttpServletRequest request, Class<T> objectType) {
        try {
            return objectMapper.reader()
                    .forType(objectType)
                    .readValue(((CachedBodyHttpServletRequest) request).getReader());
        } catch (IOException e) {
            log.info("Can`t parsing json.");
            throw new RuntimeException();
        }
    }
}
