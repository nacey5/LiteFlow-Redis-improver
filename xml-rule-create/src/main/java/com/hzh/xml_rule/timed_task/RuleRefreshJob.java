
package com.hzh.xml_rule.timed_task;

import com.hzh.xml_rule.controller.rule.RuleChangeController;
import com.hzh.xml_rule.util.SpringBeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author dahuang
 * @version : RuleRefresh.java, v 0.1 2023-06-19 19:29 dahuang
 */

@Slf4j
public class RuleRefreshJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            //这里必须在外部上下文引入，通过.class文件注入的方式会绕过springboot的检查
            RuleChangeController ruleChangeController = SpringBeanUtils.getBean(RuleChangeController.class);
            log.info("start monitor chang rule");
            ruleChangeController.changeRule();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}