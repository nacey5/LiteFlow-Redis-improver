package com.hzh.filter;

import com.hzh.util.ColorAloUtil;
import com.hzh.util.OrderedUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * @author dahuang
 * @version : RequestFilter.java, v 0.1 2023-06-26 22:38 dahuang
 */
@Component
@Slf4j
public class RequestFilter implements GlobalFilter, Ordered {

    @Value("${uri.default}")
    private String defaultURI;

    @Value("${uri.blue}")
    private String blueURI;
    @Value("${uri.green}")
    private String greenURI;

    private Integer count=0;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String routeUri = exchange.getRequest().getURI().toString();
        String label = ColorAloUtil.selectColor(new String[] {"GREEN", "BLUE"}, new int[] {50, 100});
        String targetUri = "";

        // 根据路由URI进行逻辑处理
        if (!routeUri.contains(defaultURI)) {
            // 根据服务标签进行灰度处理
            if (StringUtils.isNotBlank(exchange.getAttribute("color"))) {
                if ("green".equals(exchange.getAttribute("color"))&&count==0){
                    count++;
                    // 在灰度环境中执行相应逻辑
                    targetUri = blueURI;
                    targetUri = routeUri.replace(defaultURI, targetUri);
                    targetUri = targetUri.replace("/api", "/green");
                    targetUri = targetUri.replace("/blue", "/green");
                    log.warn("targetUri->green:{}", targetUri);
                }else {
                    count--;
                    // 在正常环境中执行相应逻辑
                    targetUri = greenURI;
                    targetUri = routeUri.replace(defaultURI, targetUri);
                    targetUri = targetUri.replace("/api", "/blue");
                    targetUri = targetUri.replace("/green", "/blue");
                    log.warn("targetUri->blue:{}", targetUri);
                }
            }else {
                if (label != null && label.contains(GrayFilter.Config.COMMON_COLOR)) {
                    // 在灰度环境中执行相应逻辑
                    targetUri = blueURI;
                    targetUri = routeUri.replace(defaultURI, targetUri);
                    targetUri = targetUri.replace("/api", "/green");
                    log.warn("targetUri->green:{}", targetUri);
                    // ...
                } else {
                    // 在正常环境中执行相应逻辑
                    targetUri = greenURI;
                    targetUri = routeUri.replace(defaultURI, targetUri);
                    targetUri = targetUri.replace("/api", "/blue");
                    log.warn("targetUri->blue:{}", targetUri);
                    // ...
                }
            }

            // 构建新的URI，将请求发送到 router1
            URI newUri = UriComponentsBuilder.fromUriString(targetUri).build().toUri();
            ServerHttpRequest request = exchange.getRequest().mutate().uri(newUri).build();

            // 转发请求到新的URI
            return chain.filter(exchange.mutate().request(request).build());
        }

        // 继续执行后续的过滤器和处理链
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return OrderedUtil.FIRST; // 定义过滤器的执行顺序
    }
}
