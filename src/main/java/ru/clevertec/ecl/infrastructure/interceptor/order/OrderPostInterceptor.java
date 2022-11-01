package ru.clevertec.ecl.infrastructure.interceptor.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.clevertec.ecl.api.order.OrderPostDto;
import ru.clevertec.ecl.api.order.OrderReadDto;
import ru.clevertec.ecl.infrastructure.interceptor.NodeInterceptor;
import ru.clevertec.ecl.infrastructure.interceptor.router.Router;
import ru.clevertec.ecl.infrastructure.node.Node;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Log4j2
@RequiredArgsConstructor
public class OrderPostInterceptor implements NodeInterceptor {

    private final Node node;
    private final RestTemplate restTemplate;
    private final AtomicInteger counter;

    @Autowired
    public OrderPostInterceptor(Node node, RestTemplateBuilder restTemplateBuilder) {
        this(node, restTemplateBuilder.build(), new AtomicInteger(0));
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        boolean condition = (counter.getAndIncrement() % (node.getCapacity() - node.getNumber() + 1)) != 0;
        if (!node.isLast() && condition) {
            ResponseEntity<OrderReadDto> responseEntity = restTemplate.postForEntity(
                     node.getNextNodeUrl() + request.getRequestURI(),
                    jsonBody(request, OrderPostDto.class),
                    OrderReadDto.class
            );
            Router.responseEntityRouter(responseEntity).toResponse(response);
            return false;
        }
        return true;
    }
}
