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
package com.hzh.xml_rule.sub_pub;

import com.hzh.xml_rule.controller_rule_exchange.RuleChange;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.redisson.api.listener.MessageListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author dahuang
 * @version : MyMessageListener.java, v 0.1 2023-06-11 23:27 dahuang
 */
@Component
@Slf4j
public class MyMessageListener implements MessageListener<String> {

    @Resource
    private RuleChange ruleChange;

    @Override
    public void onMessage(CharSequence charSequence, String message) {
        //处理接收到的消息
        if (StringUtils.isBlank(message)){
            return;
        }
        String[] split = message.split("#");
        log.warn("the new rule:{}",split[1]);
        try {
            ruleChange.changeRule();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}