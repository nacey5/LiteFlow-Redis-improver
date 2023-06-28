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
package com.hzh.xml_rule.service.impl;

import com.hzh.liteflow_redis.service.AkRedisService;
import com.hzh.xml_rule.service.IAkService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author dahuang
 * @version : IAkServiceImpl.java, v 0.1 2023-06-29 00:52 dahuang
 */
@Service
public class IAkServiceImpl implements IAkService {

    @Resource
    private AkRedisService akRedisService;

    @Override
    public String saveTheAkInfoToRedis(String ak, String sk) {
        try {
            akRedisService.setValue(ak,sk);
        }catch (Exception e){
            throw new RuntimeException("save the ak info to redis failed");
        }
        return ak;
    }
}