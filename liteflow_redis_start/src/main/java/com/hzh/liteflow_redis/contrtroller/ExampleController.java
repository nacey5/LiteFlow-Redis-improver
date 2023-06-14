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
package com.hzh.liteflow_redis.contrtroller;

import com.hzh.liteflow_redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dahuang
 * @version : ExampleController.java, v 0.1 2023-06-04 12:03 dahuang
 */

@RestController
public class ExampleController {
    @Autowired
    private  RedisService redisService;

    public ExampleController(RedisService redisService) {
        this.redisService = redisService;
    }

    @GetMapping("/redis/{key}")
    public String getValueFromRedis(@PathVariable String key) {
        return redisService.getValue(key);
    }

    @GetMapping("/redis/{key}/{value}")
    public void setValueInRedis(@PathVariable String key, @PathVariable String value) {
        redisService.setValue(key, value);
    }
}