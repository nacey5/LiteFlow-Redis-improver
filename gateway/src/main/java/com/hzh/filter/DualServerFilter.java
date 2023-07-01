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
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

@Component
public class DualServerFilter implements GlobalFilter, Ordered {

    @Resource
    private LoadBalancerClient loadBalancerClient;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String routeUri = exchange.getRequest().getURI().toString();

        // 在特殊情况下，将请求同时发送到两个目标服务器
        if (shouldSendToDualServers(routeUri)) {
            // 发送第一个请求
            ServerHttpRequest request1 = exchange.getRequest().mutate()
                .uri(loadBalancerClient.choose("green_route").getUri())
                .build();

            // 发送第二个请求
            ServerHttpRequest request2 = exchange.getRequest().mutate()
                .uri(loadBalancerClient.choose("blue_route").getUri())
                .build();

            // 将两个请求发送到目标服务器
            Mono<Void> response1 = chain.filter(exchange.mutate().request(request1).build());
            Mono<Void> response2 = chain.filter(exchange.mutate().request(request2).build());

            // 合并两个响应流
            return Mono.zip(response1, response2).then();
        }

        // 普通情况下，只发送请求到一个目标服务器
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        // 设置过滤器的顺序
        return OrderedUtil.MIN_CURE;
    }

    private boolean shouldSendToDualServers(String routeUri) {
        // 根据条件判断是否发送请求到两个目标服务器
        // 根据你的业务需求进行实现
        // 这里只是一个示例 todo 后续要将分隔内容在这里进行判断
        return routeUri.contains("/beans/register-bean");
    }
}
