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

import com.hzh.liteflow_redis.listener.sub_pub.MyRedisSubscriber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dahuang
 * @version : RedisService.java, v 0.1 2023-06-04 01:24 dahuang
 */
@Service
@Slf4j
public class RedisService {

    @Resource
    private RedissonClient redissonClient;
    @Resource
    private MyRedisSubscriber myRedisSubscriber;


    public RedisService(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    public void setValue(String key, String value) {
        redissonClient.getBucket(key).set(value);
        myRedisSubscriber.sendMessage(MyRedisSubscriber.TOPIC,key+"#"+value);
        log.info("success");
    }

    public String getValue(String key) {
        return (String)redissonClient.getBucket(key).get();
    }

    public List<String> keys() {
        return redissonClient.getKeys().getKeysStream().collect(Collectors.toList());
    }
}