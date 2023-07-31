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
import com.hzh.main.config.EnforcerConfigProperties;
import org.casbin.adapter.JDBCAdapter;
import org.casbin.jcasbin.main.Enforcer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author dahuang
 * @version : EnforcerFactory.java, v 0.1 2023-07-28 00:43 dahuang
 */
//@Component
public class EnforcerFactory implements InitializingBean {

    private static Enforcer enforcer;

    @Autowired
    private EnforcerConfigProperties enforcerConfigProperties;
    private static EnforcerConfigProperties config;

    @Override
    public void afterPropertiesSet() throws Exception {
        config = enforcerConfigProperties;
        //从数据库读取策略
        JDBCAdapter jdbcAdapter = new JDBCAdapter(config.getDriverClassName(),config.getUrl(),config.getUsername(),
            config.getPassword(), true);
        enforcer = new Enforcer(config.getModelPath(), jdbcAdapter);
        enforcer.loadPolicy();//Load the policy from DB.
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