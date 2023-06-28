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
package com.hzh.xml_rule.manager.tran.impl;

import com.hzh.all.util.AKUtil;
import com.hzh.holder.GlobalAKHolder;
import com.hzh.xml_rule.manager.tran.AkManager;
import com.hzh.xml_rule.service.IAkService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @author dahuang
 * @version : AkManagerImpl.java, v 0.1 2023-06-29 00:36 dahuang
 */
@Component
public class AkManagerImpl implements AkManager {

    //cache
    @Resource
    private GlobalAKHolder globalAKHolder;
    @Resource
    private IAkService iAkService;
    @Override
    public String applyAk() {
        HashMap<String, String> infoMap = AKUtil.generateAk();
        String ak = infoMap.get(AKUtil.AK_KEY);
        String sk=infoMap.get(AKUtil.SK_KEY);
        String saveAk="";
        //1.先存入redis数据
        try {
            saveAk = iAkService.saveTheAkInfoToRedis(ak, sk);
            if (StringUtils.isBlank(saveAk)) {
                throw new RuntimeException("保存ak数据错误:");
            }
        }catch (Exception e){
            throw new RuntimeException("保存ak数据错误:");
        }
        //2.存入成功再加载进入缓存
        globalAKHolder.setData(saveAk,sk);
        return ak;
    }
}