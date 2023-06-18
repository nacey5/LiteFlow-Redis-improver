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

import com.hzh.xml_rule.service.IBeanService;
import com.yomahub.liteflow.spring.ComponentScanner;
import org.springframework.stereotype.Service;

/**
 * @author dahuang
 * @version : BeanService.java, v 0.1 2023-06-18 13:28 dahuang
 */
@Service
public class BeanService implements IBeanService {
    @Override
    public void scanIntoSpringboot(Object o,String beanName){
        ComponentScanner componentScanner = new ComponentScanner();
        componentScanner.postProcessBeforeInitialization(o, beanName);
        componentScanner.postProcessAfterInitialization(o, beanName);
    }
}