package ru.clevertec.ecl.infrastructure.interceptor.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import ru.clevertec.ecl.infrastructure.interceptor.DeleteInterceptor;
import ru.clevertec.ecl.infrastructure.interceptor.GetInterceptor;
import ru.clevertec.ecl.infrastructure.interceptor.MethodInterceptor;
import ru.clevertec.ecl.infrastructure.interceptor.PostInterceptor;
import ru.clevertec.ecl.infrastructure.interceptor.PutInterceptor;
import ru.clevertec.ecl.infrastructure.interceptor.order.OrderDeleteInterceptor;
import ru.clevertec.ecl.infrastructure.interceptor.order.OrderGetInterceptor;
import ru.clevertec.ecl.infrastructure.interceptor.order.OrderPostInterceptor;
import ru.clevertec.ecl.infrastructure.interceptor.order.OrderPutInterceptor;

import java.util.EnumMap;

@Configuration
@RequiredArgsConstructor
public class InterceptorConfiguration extends WebMvcConfigurationSupport {

    private final GetInterceptor getInterceptor;
    private final DeleteInterceptor deleteInterceptor;
    private final PostInterceptor postInterceptor;
    private final PutInterceptor putInterceptor;

    private final OrderGetInterceptor orderGetInterceptor;
    private final OrderDeleteInterceptor orderDeleteInterceptor;
    private final OrderPostInterceptor orderPostInterceptor;
    private final OrderPutInterceptor orderPutInterceptor;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        EnumMap<HttpMethod, HandlerInterceptor> router = new EnumMap<>(HttpMethod.class);
        router.put(HttpMethod.GET, getInterceptor);
        router.put(HttpMethod.DELETE, deleteInterceptor);
        router.put(HttpMethod.POST, postInterceptor);
        router.put(HttpMethod.PUT, putInterceptor);
        EnumMap<HttpMethod, HandlerInterceptor> orderRouter = new EnumMap<>(HttpMethod.class);
        orderRouter.put(HttpMethod.GET, orderGetInterceptor);
        orderRouter.put(HttpMethod.DELETE, orderDeleteInterceptor);
        orderRouter.put(HttpMethod.POST, orderPostInterceptor);
        orderRouter.put(HttpMethod.PUT, orderPutInterceptor);
        registry.addInterceptor(new MethodInterceptor(router))
                .addPathPatterns(
                        "/**/tags/?",
                        "/**/tags",
                        "/**/certificates",
                        "/**/certificates/?",
                        "/**/users",
                        "/**/users/?"
                );
        registry.addInterceptor(new MethodInterceptor(orderRouter))
                .addPathPatterns(
                        "/**/orders",
                        "/**/orders/?"
                );
    }
}
