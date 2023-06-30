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

import com.hzh.holder.GlobalAKHolder;
import com.hzh.liteflow_redis.service.AkRedisService;
import com.hzh.util.OrderedUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;

/**
 * @author dahuang
 * @version : AKValidationFilter.java, v 0.1 2023-06-27 14:28 dahuang
 */
@Component
public class AKValidationFilter implements GlobalFilter, Ordered {

    private static final String AK_HEADER = "AK"; // AK在请求头中的字段名
    private static final String APPLY_AK_PATH =
        "/api/ak/applyAK | /gray/ak/applyAK"; // 申请AK的路径

    private final GlobalAKHolder globalAKHolder;

    private final AkRedisService akRedisService;

    public AKValidationFilter(GlobalAKHolder globalAKHolder,
        @Qualifier("AkRedisService") AkRedisService akRedisService) {
        this.globalAKHolder = globalAKHolder;
        this.akRedisService = akRedisService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获取请求的URI和请求头中的AK信息
        String requestUri = exchange.getRequest().getPath().toString();
        String ak = exchange.getRequest().getHeaders().getFirst(AK_HEADER);

        // 检查是否是申请AK的路径，如果是则直接放行
        if (validPath(requestUri)) {
            return chain.filter(exchange);
        }

        // 根据实际情况进行AK校验逻辑，这里只做简单示例
        if (StringUtils.isBlank(ak) || !isValidAK(ak)) {
            // AK无效，返回错误响应
            return handleInvalidAKResponse(exchange);
        }

        // AK有效，继续处理后续的过滤器和处理链
        return chain.filter(exchange);
    }

    private boolean validPath(String requestUri) {
        String[] allPath = APPLY_AK_PATH.split("\\s*\\|\\s*");
        return Arrays.stream(allPath).anyMatch(a -> a.equals(requestUri));
    }

    // 实际的AK校验逻辑，根据自己的业务需求进行实现
    private boolean isValidAK(String ak) {
        //根据AK进行有效性校验的逻辑
        boolean ret = false;
        ret = authAkInCache(ak);
        ret = ret || getAkInRedis(ak);
        // 这里只做示例，始终返回true，表示AK有效
        return ret;
    }

    private boolean getAkInRedis(String ak) {
        boolean ret=false;
        String data = akRedisService.getValue(ak);
        if (StringUtils.isNotBlank(data)) {
            ret=true;
            globalAKHolder.setData(ak,data);
        }
        return ret;
    }

    private boolean authAkInCache(String ak) {
        String data = globalAKHolder.getData(ak);
        return StringUtils.isNotBlank(data);
    }

    // 处理无效AK的响应
    private Mono<Void> handleInvalidAKResponse(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED); // 设置响应状态码为401 Unauthorized
        return response.setComplete();
    }

    @Override
    public int getOrder() {
        // 设置过滤器的执行顺序，可以根据实际需求调整顺序
        return OrderedUtil.MAX_CURE;
    }
}
