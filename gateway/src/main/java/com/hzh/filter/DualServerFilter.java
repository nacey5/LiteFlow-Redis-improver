/*
 * Aloudata.com Inc.
 * Copyright (c) 2021-2023 All Rights Reserved.
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hzh.filter;

/**
 * @author dahuang
 * @version : DualServerFilter.java, v 0.1 2023-07-02 00:41 dahuang
 */
import com.hzh.util.OrderedUtil;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import java.net.URI;

@Component
public class DualServerFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String routeUri = exchange.getRequest().getURI().toString();
        URI newUri = UriComponentsBuilder.fromUriString(routeUri).build().toUri();
        ServerHttpRequest request = exchange.getRequest().mutate().uri(newUri).build();

        // 在特殊情况下，将请求同时发送到两个目标服务器
        if (shouldSendToDualServers(routeUri)) {
           //这个在开启服务注册和发现之后再进行修改，在没有开启服务注册和发现之前，loadBalance无法发挥作用
            // 创建蓝色路由的请求副本
            ServerHttpRequest blueRequest = request.mutate()
                .uri(request.getURI().resolve(solveURI(routeUri,"blue")))
                .headers(httpHeaders -> httpHeaders.set("color", "blue")) // 设置蓝色路由的header
                .build();

            // 创建蓝色路由的ServerWebExchange对象
            ServerWebExchange blueExchange = exchange.mutate()
                .request(blueRequest)
                .build();
            blueExchange.getAttributes().put("color","blue");

            // 创建绿色路由的请求副本
            ServerHttpRequest greenRequest = request.mutate()
                .uri(request.getURI().resolve(solveURI(routeUri,"green")))
                .headers(httpHeaders -> httpHeaders.set("color", "green"))
                .build();

            // 创建绿色路由的ServerWebExchange对象
            ServerWebExchange greenExchange = exchange.mutate()
                .request(greenRequest)
                .build();
            blueExchange.getAttributes().put("color","green");

//            // 分别调用过滤器链，将请求转发到蓝色和绿色路由
//            Mono<Void> blueResponse = chain.filter(blueExchange);
//            Mono<Void> greenResponse = chain.filter(greenExchange);

            return chain.filter(blueExchange)
                .flatMap(blueResponse -> chain.filter(greenExchange)
                    .thenReturn(blueResponse));
        }

        // 普通情况下，只发送请求到一个目标服务器
        return chain.filter(exchange);
    }

    private URI solveURI(String routeUri, String color) {
        if ("green".equals(color)){
            routeUri = routeUri.replace(":9899", ":9888");
        }else {
            routeUri = routeUri.replace(":9888", ":9899");
        }
        return UriComponentsBuilder.fromUriString(routeUri).build().toUri();
    }

    @Override
    public int getOrder() {
        // 设置过滤器的顺序
        return OrderedUtil.MAX_CURE;
    }

    private boolean shouldSendToDualServers(String routeUri) {
        // 根据条件判断是否发送请求到两个目标服务器
        // 根据你的业务需求进行实现
        // 这里只是一个示例 todo 后续要将分隔内容在这里进行判断
        return routeUri.contains("/beans/register-bean");
    }
}
