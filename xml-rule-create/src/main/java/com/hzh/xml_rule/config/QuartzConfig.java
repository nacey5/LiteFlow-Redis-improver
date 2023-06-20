
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
