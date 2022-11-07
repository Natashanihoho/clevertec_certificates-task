package ru.clevertec.ecl.infrastructure.interceptor.configuration;

import java.util.EnumMap;
import java.util.List;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import lombok.RequiredArgsConstructor;
import ru.clevertec.ecl.infrastructure.interceptor.*;
import ru.clevertec.ecl.infrastructure.interceptor.order.OrderGetInterceptor;
import ru.clevertec.ecl.infrastructure.interceptor.order.OrderPostInterceptor;

@ConditionalOnProperty(prefix = "node", name = "number", havingValue = "1")
@Configuration
@RequiredArgsConstructor
public class InterceptorConfiguration extends WebMvcConfigurationSupport {

    private final GetInterceptor getInterceptor;
    private final DeleteInterceptor deleteInterceptor;
    private final PostInterceptor postInterceptor;
    private final PutInterceptor putInterceptor;

    private final OrderGetInterceptor orderGetInterceptor;
    private final OrderPostInterceptor orderPostInterceptor;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        EnumMap<HttpMethod, HandlerInterceptor> router = new EnumMap<>(HttpMethod.class);
        router.put(HttpMethod.GET, getInterceptor);
        router.put(HttpMethod.DELETE, deleteInterceptor);
        router.put(HttpMethod.POST, postInterceptor);
        router.put(HttpMethod.PUT, putInterceptor);
        EnumMap<HttpMethod, HandlerInterceptor> orderRouter = new EnumMap<>(HttpMethod.class);
        orderRouter.put(HttpMethod.GET, orderGetInterceptor);
        orderRouter.put(HttpMethod.POST, orderPostInterceptor);
        registry.addInterceptor(new MethodInterceptor(router))
                .addPathPatterns(
                        "/**/tags/?",
                        "/**/tags/**",
                        "/**/certificates/**",
                        "/**/certificates/?",
                        "/**/users/**",
                        "/**/users/?"
                );
        registry.addInterceptor(new MethodInterceptor(orderRouter))
                .addPathPatterns(
                        "/**/orders**",
                        "/**/orders/?"
                );
    }

    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new PageableHandlerMethodArgumentResolver());
    }

}
