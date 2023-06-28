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
package com.hzh.holder;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author dahuang
 * @version : GlobalAKHolder.java, v 0.1 2023-06-29 00:07 dahuang
 */
@Component
public class GlobalAKHolder {

    private final ConcurrentHashMap<String, String> akMap = new ConcurrentHashMap<>();

    public Object getData(String key) {
        return akMap.get(key);
    }

    public void setData(String key, String value) {
        akMap.put(key, value);
    }

    public void removeData(String key) {
        akMap.remove(key);
    }
}