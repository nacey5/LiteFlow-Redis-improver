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
package com.hzh.xml_rule.config;

import com.hzh.xml_rule.sub_pub.MyMessageListener;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

import static com.hzh.liteflow_redis.listener.sub_pub.MyRedisSubscriber.TOPIC;

/**
 * @author dahuang
 * @version : RedissonConfig.java, v 0.1 2023-06-11 23:45 dahuang
 */
@Configuration
public class RedissonConfig {

    @Resource
    private MyMessageListener messageReceiver;

    @Resource
    private RedissonClient redissonClient;

    @Bean
    public void registerMessageListener() {
        RTopic topic = redissonClient.getTopic(TOPIC);
        topic.addListener(String.class,messageReceiver);
    }
}






