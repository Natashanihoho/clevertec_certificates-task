package ru.clevertec.ecl.infrastructure.interceptor.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.clevertec.ecl.api.order.OrderPostDto;
import ru.clevertec.ecl.api.order.OrderReadDto;
import ru.clevertec.ecl.infrastructure.interceptor.NodeInterceptor;
import ru.clevertec.ecl.infrastructure.interceptor.router.Router;
import ru.clevertec.ecl.infrastructure.node.IdHandler;
import ru.clevertec.ecl.infrastructure.node.Node;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Log4j2
@RequiredArgsConstructor
public class OrderPutInterceptor implements NodeInterceptor {

    private final RestTemplate restTemplate;
    private final Node node;
    private final IdHandler predicate;

    @Autowired
    public OrderPutInterceptor(RestTemplateBuilder restTemplateBuilder, Node node) {
        this(restTemplateBuilder.build(), node, new IdHandler(node));
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info("Node - " + node.getNumber() + " Method - " + HttpMethod.PUT);
        if (!predicate.isCurrentNode(request)) {
            ResponseEntity<OrderReadDto> responseEntity = restTemplate.exchange(
                    node.getNextNodeUrl() + request.getRequestURI(),
                    HttpMethod.PUT,
                    new HttpEntity<>(jsonBody(request, OrderPostDto.class)),
                    OrderReadDto.class
            );
            Router.responseEntityRouter(responseEntity).toResponse(response);
        }
        return true;
    }
}
