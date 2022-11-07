package ru.clevertec.ecl.infrastructure.interceptor;

import java.util.concurrent.CompletableFuture;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ru.clevertec.ecl.infrastructure.node.Node;

@Component
@RequiredArgsConstructor
@Log4j2
public class PutInterceptor implements HandlerInterceptor {

    private final RestTemplate restTemplate;
    private final Node node;
    private final JsonSerializer jsonSerializer;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info("Node - " + node.getNumber() + " Method - " + HttpMethod.PUT);
        node.getSubNodes().stream()
                .map(Node.SubNode::getUrl)
                .forEach(url ->
                        CompletableFuture.runAsync(
                                () -> restTemplate.put(
                                        url + request.getRequestURI(),
                                        jsonSerializer.jsonObject(request, Object.class)
                                )
                        )
                );
        return true;
    }
}
