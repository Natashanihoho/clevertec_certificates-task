package ru.clevertec.ecl.infrastructure.interceptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.clevertec.ecl.infrastructure.node.Node;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
@Log4j2
public class PutInterceptor implements NodeInterceptor {

    private final RestTemplate restTemplate;
    private final Node node;

    @Autowired
    public PutInterceptor(RestTemplateBuilder restTemplateBuilder, Node node) {
        this(restTemplateBuilder.build(), node);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info("Node - " + node.getNumber() + " Method - " + HttpMethod.PUT);
        if (node.isMain()) {
            node.getUrls()
                    .forEach(url ->
                            CompletableFuture.runAsync(() ->
                                    restTemplate.exchange(
                                            url + request.getRequestURI(),
                                            HttpMethod.PUT,
                                            new HttpEntity<>(jsonBody(request)),
                                            Object.class
                                    )
                            )
                    );
        }
        return true;
    }
}
