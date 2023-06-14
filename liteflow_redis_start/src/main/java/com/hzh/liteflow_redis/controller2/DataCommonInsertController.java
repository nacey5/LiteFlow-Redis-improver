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
package com.hzh.liteflow_redis.controller2;

import com.hzh.liteflow_redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author dahuang
 * @version : DataCommonInsertController.java, v 0.1 2023-06-04 13:16 dahuang
 */

@RestController
public class DataCommonInsertController {

    @Autowired
    private RedisService redisService;

    public DataCommonInsertController(RedisService redisService) {
        this.redisService = redisService;
    }


    @PostMapping("/redis/get/{key}")
    public String getValueFromRedis(@PathVariable String key) {
        return redisService.getValue(key);
    }

    @PostMapping("/redis/put/{key}")
    public void setValueInRedis(@PathVariable String key, @RequestBody String value) {
        redisService.setValue(key, value);
    }
}