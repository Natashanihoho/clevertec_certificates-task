package ru.clevertec.ecl.infrastructure.interceptor.order;

import static ru.clevertec.ecl.infrastructure.interceptor.order.url.Attribute.ID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ru.clevertec.ecl.api.order.OrderReadDto;
import ru.clevertec.ecl.infrastructure.interceptor.order.url.UrlPathParser;
import ru.clevertec.ecl.infrastructure.node.Node;

@Component
@Log4j2
@RequiredArgsConstructor
public class OrderGetInterceptor implements OrderInterceptor {

    private final Node node;
    private final SequenceProvider sequenceProvider;
    private final RestTemplate restTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info("Node - " + node.getNumber() + " Method - " + HttpMethod.GET);
        final UrlPathParser urlPathParser = new UrlPathParser(request);
        if (urlPathParser.isAttribute(ID)) {
            if (isNotCurrentNode(urlPathParser.getIdAttribute())) {
                routeResponseEntityToResponse(findOrder(urlPathParser, request), response);
                return false;
            }
        } else {
            if (urlPathParser.arePaginationParameterExisted()) {
                routeResponseEntityToResponse(
                        sequenceProvider.findPaginationEntities(urlPathParser),
                        response
                );
                return false;
            }
        }
        return true;
    }

    private ResponseEntity<OrderReadDto> findOrder(UrlPathParser urlPathParser, HttpServletRequest request) {
        return node.getSubNodes().stream()
                .filter(subNodes -> condition(urlPathParser.getIdAttribute()) == subNodes.getNumber())
                .map(Node.SubNode::getUrl)
                .map(url -> restTemplate.getForEntity(url + request.getRequestURI(), OrderReadDto.class))
                .findFirst()
                .orElse(ResponseEntity.notFound().build());
    }

    private boolean isNotCurrentNode(int id) {
        return condition(id) != node.getNumber();
    }

    private int condition(int id) {
        return ((id - 1) % node.getCapacity()) + 1;
    }

}
