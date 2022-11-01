package ru.clevertec.ecl.infrastructure.interceptor.order;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import ru.clevertec.ecl.infrastructure.interceptor.NodeInterceptor;
import ru.clevertec.ecl.infrastructure.node.IdHandler;
import ru.clevertec.ecl.infrastructure.node.Node;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Log4j2
@RequiredArgsConstructor
public class OrderGetInterceptor implements NodeInterceptor {

    private final IdHandler idHandler;
    private final Node node;

    @Autowired
    public OrderGetInterceptor(Node node) {
        this(new IdHandler(node), node);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("Node - " + node.getNumber() + " Method - " + HttpMethod.GET);
        if (!idHandler.isCurrentNode(request)) {
            response.sendRedirect(node.getNextNodeUrl() + request.getRequestURI());
            return false;
        }
        response.sendRedirect(node.getNextNodeUrl() + idHandler.getOrderUri(request));
        return true;
    }
}
