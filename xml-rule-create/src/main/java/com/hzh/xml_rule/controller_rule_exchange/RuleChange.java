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
package com.hzh.xml_rule.controller_rule_exchange;

import com.hzh.xml_rule.parse.MyParser;
import com.hzh.xml_rule.pre_load.MsgHandle;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @author dahuang
 * @version : RuleChange.java, v 0.1 2023-06-11 19:50 dahuang
 */
@RestController
@RequestMapping("change")
public class RuleChange {

    @Resource
    private MyParser myParser;

    @GetMapping("/change")
    public void changeRule() throws Exception {
        myParser.parseRedis();
    }

    @GetMapping("/pre_load")
    public void preLoad(){
        new Thread(new MsgHandle()).start();
    }
}