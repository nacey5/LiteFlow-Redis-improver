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
package com.hzh.liteflow_redis.instance;

/**
 * @author dahuang
 * @version : RedisConfiguration.java, v 0.1 2023-06-29 00:59 dahuang
 */
import com.hzh.liteflow_redis.factory.ConfigFactory;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.FstCodec;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class RedisConfiguration {

    @Bean
    @Primary
    public RedissonClient redissonClient1() {
        Config config = ConfigFactory.newDefaultRedissionConfig();
        config.useSingleServer()
            .setAddress("redis://127.0.0.1:6379")
            .setDatabase(1);
        return Redisson.create(config);
    }

    @Bean
    public RedissonClient redissonClient2() {
        Config config = ConfigFactory.newDefaultRedissionConfig();
        config.useSingleServer()
            .setAddress("redis://127.0.0.1:6379")
            .setDatabase(2);
        return Redisson.create(config);
    }
}
