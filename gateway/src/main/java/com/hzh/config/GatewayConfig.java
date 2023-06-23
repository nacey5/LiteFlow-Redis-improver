
package com.hzh.config;

/**
 * @author dahuang
 * @version : GatewayConfig.java, v 0.1 2023-06-23 23:56 dahuang
 */
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import org.springframework.web.server.WebFilter;
import reactor.core.publisher.Mono;

@Configuration
public class GatewayConfig implements WebFluxConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods("*")
            .allowedHeaders("*");
    }

    @Bean
    public WebFilter customFilter() {
        return (ServerWebExchange exchange, WebFilterChain chain) -> {
            // 在这里添加自定义的过滤逻辑
            // ...
            return chain.filter(exchange);
        };
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("example_route", r -> r.path("/api")
                .uri("http://127.0.0.1:8080"))
            .build();
    }

    @Bean
    public WebFilter errorFilter() {
        return (ServerWebExchange exchange, WebFilterChain chain) -> {
            ServerHttpResponse response = exchange.getResponse();
            if (response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
                // 处理错误响应
                // ...
                return Mono.empty();
            }
            return chain.filter(exchange);
        };
    }
}
