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
package com.hzh.xml_rule.util;

import com.hzh.all.GrayScheduling;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dahuang
 * @version : NodeColorCache.java, v 0.1 2023-06-23 14:33 dahuang
 */
public class NodeColorCacheFactory{

    public static Map<GrayScheduling, String> buildNewCacheMap(){
        Map<GrayScheduling, String> map=new HashMap<>();
        initialize(map);
        return map;
    }


    private static void initialize(Map<GrayScheduling, String>  map) {
        map.put(GrayScheduling.DEFAULT,"");
        map.put(GrayScheduling.BLUE,"");
        map.put(GrayScheduling.GREEN,"");
    }

}