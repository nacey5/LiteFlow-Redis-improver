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
package com.hzh.liteflow_redis.service;

import com.hzh.liteflow_redis.listener.sub_pub.RuleChangeRedisSubscriber;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author dahuang
 * @version : AkRedisService.java, v 0.1 2023-06-29 01:01 dahuang
 */
@Service("AkRedisService")
@Slf4j
public class AkRedisService {

    private final RedissonClient redissonClient;

    private static final long AK_EXPIRATION_SECONDS = 3600; // AK过期时间（秒）
    private static final String AK_PREFIX = "ak:";

    public AkRedisService(@Qualifier("redissonClient2") RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    public String getValue(String key) {
        String akKey = AK_PREFIX + key;
        return (String)redissonClient.getBucket(akKey).get();
    }

    public void setValue(String key, String value) {
        String akKey = AK_PREFIX + key;
        redissonClient.getBucket(akKey).set(value, AK_EXPIRATION_SECONDS, TimeUnit.SECONDS);
        log.info("save ak success");
    }
}