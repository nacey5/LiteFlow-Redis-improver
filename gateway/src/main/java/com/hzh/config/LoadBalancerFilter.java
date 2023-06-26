///*
// * Aloudata.com Inc.
// * Copyright (c) 2021-2023 All Rights Reserved.
// *
// * Licensed to the Apache Software Foundation (ASF) under one
// * or more contributor license agreements.  See the NOTICE file
// * distributed with this work for additional information
// * regarding copyright ownership.  The ASF licenses this file
// * to you under the Apache License, Version 2.0 (the
// * "License"); you may not use this file except in compliance
// * with the License.  You may obtain a copy of the License at
// *
// *     http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package com.hzh.config;
//
//import org.springframework.cloud.client.ServiceInstance;
//import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
//import org.springframework.cloud.gateway.filter.GatewayFilter;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.core.Ordered;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import org.springframework.web.util.UriComponentsBuilder;
//import reactor.core.publisher.Mono;
//
//import java.net.URI;
//
///**
// * @author dahuang
// * @version : LoadBalancerFilter.java, v 0.1 2023-06-26 22:15 dahuang
// */
//@Component
//public class LoadBalancerFilter implements GatewayFilter, Ordered {
//
//    private final LoadBalancerClient loadBalancerClient;
//
//    public LoadBalancerFilter(LoadBalancerClient loadBalancerClient) {
//        this.loadBalancerClient = loadBalancerClient;
//    }
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        URI originalUrl = exchange.getRequest().getURI();
//        String serviceName;
//
//        // 根据权重比例选择服务名
//        if (Math.random() < 0.8) {
//            serviceName = "service1";
//        } else {
//            serviceName = "service2";
//        }
//
//        // 使用负载均衡器选择服务实例
//        ServiceInstance serviceInstance = loadBalancerClient.choose(serviceName);
//        if (serviceInstance != null) {
//            URI uri = serviceInstance.getUri();
//            URI newUri = UriComponentsBuilder.fromUri(originalUrl)
//                .host(uri.getHost())
//                .port(uri.getPort())
//                .build()
//                .toUri();
//            exchange.getRequest().mutate().uri(newUri);
//        }
//
//        return chain.filter(exchange);
//    }
//
//    @Override
//    public int getOrder() {
//        return Ordered.LOWEST_PRECEDENCE;
//    }
//}
