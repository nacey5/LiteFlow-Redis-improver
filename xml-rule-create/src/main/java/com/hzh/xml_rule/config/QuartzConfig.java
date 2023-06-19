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
package com.hzh.xml_rule.config;

/**
 * @author dahuang
 * @version : QuartzConfig.java, v 0.1 2023-06-19 19:26 dahuang
 */
import com.hzh.xml_rule.timed_task.RuleRefreshJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail redisReadJobDetail() {
        return JobBuilder.newJob(RuleRefreshJob.class)
            .withIdentity("redisReadJob")
            .storeDurably()
            .build();
    }

    @Bean
    public Trigger redisReadJobTrigger() {
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
            .withIntervalInSeconds(15) // 设置任务执行间隔时间
            .repeatForever();

        return TriggerBuilder.newTrigger()
            .forJob(redisReadJobDetail())
            .withIdentity("redisReadJobTrigger")
            .withSchedule(scheduleBuilder)
            .build();
    }

    @Bean
    public Scheduler scheduler(Trigger redisReadJobTrigger, JobDetail redisReadJobDetail) throws SchedulerException {
        SchedulerFactory schedulerFactory = new org.quartz.impl.StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        scheduler.scheduleJob(redisReadJobDetail, redisReadJobTrigger);
        scheduler.start();
        return scheduler;
    }
}
