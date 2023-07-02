package com.hzh.config;

/**
 * @author dahuang
 * @version : GatewayConfig.java, v 0.1 2023-06-23 23:56 dahuang
 */

import com.hzh.filter.GrayFilter;
import com.hzh.util.ColorAloUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import org.springframework.web.server.WebFilter;
import reactor.core.publisher.Mono;

import java.net.URI;

@Configuration
@Slf4j
public class GatewayConfig implements WebFluxConfigurer {

    @Value("${uri.default}")
    private String defaultURI;

    @Value("${uri.blue}")
    private String blueURI;
    @Value("${uri.green}")
    private String greenURI;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*").allowedMethods("*").allowedHeaders("*");
    }

    @Bean
    public WebFilter customFilter() {
        return (ServerWebExchange exchange, WebFilterChain chain) -> {
            // 在这里添加自定义的过滤逻辑
            // 获取原始的请求URI
            URI originalUri = exchange.getRequest().getURI();
            // 根据需要对URI进行修改
            String newUri = modifyUri(originalUri);

            // 构建新的URI并设置到请求中
            ServerHttpRequest modifiedRequest = exchange.getRequest().mutate().uri(URI.create(newUri)).build();

            // ...
            // 将修改后的请求继续传递给下一个过滤器或路由
            return chain.filter(exchange.mutate().request(modifiedRequest).build());
        };
    }

    // 根据需要自定义修改URI的逻辑
    private String modifyUri(URI originalUri) {
        // 在这里进行URI的修改逻辑
        String routeUri = originalUri.toString();
        String label = ColorAloUtil.selectColor(new String[] {"GREEN", "BLUE"}, new int[] {50, 100});
        String targetUri = "";

        // 根据路由URI进行逻辑处理
        if (routeUri.contains(defaultURI)) {
            // 根据服务标签进行灰度处理
            if (label != null && label.contains(GrayFilter.Config.COMMON_COLOR)) {
                // 在灰度环境中执行相应逻辑
                targetUri = blueURI;
                targetUri = routeUri.replace(defaultURI, targetUri);
                targetUri = targetUri.replace("/api", "/green");
                log.warn("targetUri->blue:{}", targetUri);
                // ...
            } else {
                // 在正常环境中执行相应逻辑
                targetUri = greenURI;
                targetUri = routeUri.replace(defaultURI, targetUri);
                targetUri = targetUri.replace("/api", "/blue");
                log.warn("targetUri->green:{}", targetUri);
                // ...
            }
        }
        // 返回修改后的URI字符串

        return targetUri;
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

    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }
}
