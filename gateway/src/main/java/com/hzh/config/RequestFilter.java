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

import com.hzh.util.ColorAloUtil;
import lombok.extern.slf4j.Slf4j;
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

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String routeUri = exchange.getRequest().getURI().toString();
        String label = ColorAloUtil.selectColor(new String[] {"GREEN", "BLUE"}, new int[] {50, 100});
        String targetUri="";

        // 根据路由URI进行逻辑处理
        if (routeUri.contains(defaultURI)) {
            // 根据服务标签进行灰度处理
            if (label != null && label.contains(GrayFilter.Config.COMMON_COLOR)) {
                // 在灰度环境中执行相应逻辑
                targetUri =blueURI;
                targetUri=routeUri.replace(defaultURI,targetUri);
                log.warn("targetUri->blue:{}",targetUri);
                // ...
            } else {
                // 在正常环境中执行相应逻辑
                targetUri =greenURI;
                targetUri=routeUri.replace(defaultURI,targetUri);
                targetUri=targetUri.replace("/api","/gray");
                log.warn("targetUri->green:{}",targetUri);
                // ...
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
        return -1; // 定义过滤器的执行顺序
    }
}
