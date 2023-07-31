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

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dahuang
 * @version : MultiRoutingConfig.java, v 0.1 2023-07-03 00:14 dahuang
 */
@Configuration
public class MultiRoutingConfig {
    @Resource
    private LoadBalancerClient loadBalancerClient;

    @Resource
    private DiscoveryClient discoveryClient;

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder().filter(new MultiExchangeFilter(loadBalancerClient, discoveryClient));
    }

    private class MultiExchangeFilter implements ExchangeFilterFunction {
        private final LoadBalancerClient loadBalancerClient;
        private final DiscoveryClient discoveryClient;

        public MultiExchangeFilter(LoadBalancerClient loadBalancerClient, DiscoveryClient discoveryClient) {
            this.loadBalancerClient = loadBalancerClient;
            this.discoveryClient = discoveryClient;
        }

        @Override
        public Mono<ClientResponse> filter(ClientRequest request, ExchangeFunction next) {
            String serviceId = request.url().getHost();
            List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);

            if (instances.isEmpty()) {
                // Handle case when no instances are available
                return Mono.error(new IllegalStateException("No available instances for service: " + serviceId));
            }

            // Send request to all instances in parallel
            List<Mono<ClientResponse>> responses = instances.stream()
                .map(instance -> {
                    URI instanceUri = instance.getUri();
                    ClientRequest clientRequest = ClientRequest.from(request)
                        .url(instanceUri.resolve(request.url().toString()))
                        .build();
                    return next.exchange(clientRequest);
                })
                .collect(Collectors.toList());

            // Merge responses and take only the first one
            return Flux.zip(responses, objects -> {
                    List<ClientResponse> responseList = new ArrayList<>();
                    for (Object obj : objects) {
                        responseList.add((ClientResponse) obj);
                    }
                    return responseList;
                })
                .next()
                .flatMapMany(Flux::fromIterable)
                .next();
        }
    }

}