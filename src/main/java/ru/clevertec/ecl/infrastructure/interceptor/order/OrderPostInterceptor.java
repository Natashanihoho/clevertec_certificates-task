package ru.clevertec.ecl.infrastructure.interceptor.order;

import static ru.clevertec.ecl.infrastructure.interceptor.order.SequenceProvider.CURRENT_NODE;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ru.clevertec.ecl.api.order.OrderPostDto;
import ru.clevertec.ecl.api.order.OrderReadDto;
import ru.clevertec.ecl.infrastructure.interceptor.JsonSerializer;
import ru.clevertec.ecl.infrastructure.node.Node;

@Component
@Log4j2
@RequiredArgsConstructor
public class OrderPostInterceptor implements OrderInterceptor {

    private final Node node;
    private final RestTemplate restTemplate;
    private final SequenceProvider sequenceProvider;
    private final JsonSerializer jsonSerializer;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info("Node - " + node.getNumber() + " Method - " + HttpMethod.POST);
        final String urlWithMinSequence = sequenceProvider.findUrlWithMinSequence();
        if (!urlWithMinSequence.equals(CURRENT_NODE)) {
            routeResponseEntityToResponse(responseEntity(request, urlWithMinSequence), response);
            return false;
        }
        return true;
    }

    private ResponseEntity<OrderReadDto> responseEntity(HttpServletRequest request, String urlWithMixSequence) {
        return restTemplate.postForEntity(
                urlWithMixSequence + request.getRequestURI(),
                jsonSerializer.jsonObject(request, OrderPostDto.class),
                OrderReadDto.class
        );
    }
}
