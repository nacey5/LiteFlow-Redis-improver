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
package com.hzh.xml_rule.timed_task;

import com.hzh.xml_rule.controller.rule.RuleChangeController;
import com.hzh.xml_rule.util.SpringBeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author dahuang
 * @version : BeanSetJob.java, v 0.1 2023-07-02 13:56 dahuang
 */
@Slf4j
public class BeanSetJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}