package ru.clevertec.ecl.infrastructure.interceptor.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.clevertec.ecl.infrastructure.interceptor.NodeInterceptor;
import ru.clevertec.ecl.infrastructure.node.IdHandler;
import ru.clevertec.ecl.infrastructure.node.Node;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
@Log4j2
public class OrderDeleteInterceptor implements NodeInterceptor {

    private final RestTemplate restTemplate;
    private final Node node;
    private final IdHandler idHandler;

    @Autowired
    public OrderDeleteInterceptor(RestTemplateBuilder restTemplateBuilder, Node node) {
        this(restTemplateBuilder.build(), node, new IdHandler(node));
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info("Node - " + node.getNumber() + " Method - " + HttpMethod.DELETE);
        if (!idHandler.isCurrentNode(request)) {
            restTemplate.exchange(
                    node.getNextNodeUrl() + request.getRequestURI(),
                    HttpMethod.DELETE,
                    HttpEntity.EMPTY,
                    Void.class
            );
        }
        return true;
    }
}
