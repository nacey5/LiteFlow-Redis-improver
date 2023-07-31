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
package com.hzh.main.controller;

import com.hzh.main.bo.Policy;
import com.hzh.main.component.EnforcerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dahuang
 * @version : PolicyController.java, v 0.1 2023-07-30 16:56 dahuang
 */

@RestController
public class PolicyController {
    @PutMapping("/anon/role/per")
    public Boolean addPer() {
        EnforcerFactory.addPolicy(new Policy("alice", "/user/list", "*"));
        return true;
    }

    @DeleteMapping("/anon/role/per")
    public Boolean deletePer() {
        EnforcerFactory.removePolicy(new Policy("alice", "/user/list", "*"));
        return true;
    }
}