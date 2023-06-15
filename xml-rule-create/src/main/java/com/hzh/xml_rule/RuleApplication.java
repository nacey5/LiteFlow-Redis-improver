
package com.hzh.xml_rule;

import com.hzh.xml_rule.service.XmlGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

/**
 * @author dahuang
 * @version : RuleApplication.java, v 0.1 2023-06-04 15:08 dahuang
 */

@SpringBootApplication
@ComponentScans({
    @ComponentScan("com.hzh.liteflow_redis.listener.sub_pub"),
    @ComponentScan("com.hzh.liteflow_redis.service"),
    @ComponentScan("com.hzh.xml_rule.service"),
    @ComponentScan("com.hzh.xml_rule.component")
})
public class RuleApplication implements CommandLineRunner {

    @Autowired
    private  XmlGenerationService xmlGenerationService;

    @Override
    public void run(String... args) throws Exception {
        xmlGenerationService.generateXmlFile();
    }

    public static void main(String[] args) {
        SpringApplication.run(RuleApplication.class, args);
    }
}