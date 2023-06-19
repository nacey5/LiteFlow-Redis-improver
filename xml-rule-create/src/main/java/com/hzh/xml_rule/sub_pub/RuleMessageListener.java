
package com.hzh.xml_rule.sub_pub;

import com.hzh.xml_rule.controller.rule.RuleChangeController;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.redisson.api.listener.MessageListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author dahuang
 * @version : MyMessageListener.java, v 0.1 2023-06-11 23:27 dahuang
 */
@Component
@Slf4j
public class RuleMessageListener implements MessageListener<String> {

    @Resource
    private RuleChangeController ruleChangeController;

    @Override
    public void onMessage(CharSequence charSequence, String message) {
        //处理接收到的消息
        if (StringUtils.isBlank(message)){
            return;
        }
        String[] split = message.split("#");
        log.warn("the new rule:{}",split[1]);
        try {
            ruleChangeController.changeRule();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}