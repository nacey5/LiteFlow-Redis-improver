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
package com.hzh.liteflow_redis.listener.sub_pub;

import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author dahuang
 * @version : MyRedisSubscriber.java, v 0.1 2023-06-11 23:30 dahuang
 */
@Component
public class MyRedisSubscriber {
    @Autowired
    private RedissonClient redissonClient;

    public static final String TOPIC="rule";

    public void sendMessage(String channel, String message) {
        // 创建发布/订阅实例
        RTopic topic = redissonClient.getTopic(channel);
        // 订阅指定频道
        topic.publish(message);
    }
}