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
package com.hzh.config;

import com.hzh.mmon.BaseResponse;
import org.springframework.stereotype.Component;

/**
 * @author dahuang
 * @version : FilterConfig.java, v 0.1 2023-06-25 22:28 dahuang
 */
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class MyResponseFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 在请求被路由之前的逻辑处理
        ServerHttpRequest request = exchange.getRequest();
        // 执行一些操作，例如修改请求头或验证请求信息

        // 继续执行下一个过滤器
        return chain.filter(exchange)
            .then(Mono.fromRunnable(() -> {
                // 在请求被路由之后的逻辑处理
                ServerHttpResponse response = exchange.getResponse();
                // 修改响应，例如添加自定义的响应头

                // 检查响应状态码，如果是特定状态码则进行处理
                if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                    // 修改响应状态码和响应体
                    response.setStatusCode(HttpStatus.OK);
                    response.getHeaders().add("X-Custom-Header", "Custom Value");
                    response.writeWith(Mono.just(response.bufferFactory().wrap("Not found".getBytes())));
                }
            }));
    }

    @Override
    public int getOrder() {
        // 定义过滤器的顺序，值越小优先级越高
        return Ordered.LOWEST_PRECEDENCE;
    }
}
