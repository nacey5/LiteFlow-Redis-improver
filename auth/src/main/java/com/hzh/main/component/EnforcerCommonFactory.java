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
package com.hzh.main.component;

import com.hzh.main.bo.Policy;
import org.casbin.jcasbin.main.Enforcer;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author dahuang
 * @version : EnforcerCommonFactory.java, v 0.1 2023-07-30 15:50 dahuang
 */

@Component
public class EnforcerCommonFactory {

    private static Enforcer enforcer;


    @PostConstruct
    public void init() {
        enforcer = new Enforcer("auth/src/main/resources/basic_model.conf", "auth/src/main/resources/basic_policy.csv");
    }

    /**
     * 添加权限
     * @param policy
     * @return
     */
    public static boolean addPolicy(Policy policy){
        boolean addPolicy = enforcer.addPolicy(policy.getSub(),policy.getObj(),policy.getAct());
        enforcer.savePolicy();

        return addPolicy;
    }

    /**
     * 删除权限
     * @param policy
     * @return
     */
    public static boolean removePolicy(Policy policy){
        boolean removePolicy = enforcer.removePolicy(policy.getSub(),policy.getObj(),policy.getAct());
        enforcer.savePolicy();
        return removePolicy;
    }

    public static Enforcer getEnforcer(){
        return enforcer;
    }
}