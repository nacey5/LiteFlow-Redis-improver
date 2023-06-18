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

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author dahuang
 * @version : EnableConfig.java, v 0.1 2023-06-18 13:46 dahuang
 */
@Configuration
@EnableAspectJAutoProxy
@ComponentScans({
    @ComponentScan("com.hzh.liteflow_redis.listener.sub_pub"),
    @ComponentScan("com.hzh.liteflow_redis.service"),
    @ComponentScan("com.hzh.xml_rule.service"),
    @ComponentScan("com.hzh.xml_rule.component"),
    @ComponentScan("com.hzh.xml_rule.config.**")
})
public class EnableConfig {
}