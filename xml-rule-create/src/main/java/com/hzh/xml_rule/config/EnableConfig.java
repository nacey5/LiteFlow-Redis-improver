
package com.hzh.xml_rule.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author dahuang
 * @version : EnableConfig.java, v 0.1 2023-06-18 13:46 dahuang
 */
@Configuration
@EnableAspectJAutoProxy
@ComponentScans({
    @ComponentScan("com.hzh.liteflow_redis.listener.sub_pub"),
    @ComponentScan("com.hzh.liteflow_redis.service"),
    @ComponentScan("com.hzh.xml_rule.service"),
    @ComponentScan("com.hzh.xml_rule.component"),
    @ComponentScan("com.hzh.xml_rule.config.**")
})
public class EnableConfig {
}