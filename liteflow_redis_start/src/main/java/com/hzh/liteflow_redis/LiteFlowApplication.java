
package com.hzh.liteflow_redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author dahuang
 * @version : LiteFlowApplication.java, v 0.1 2023-06-04 12:17 dahuang
 */
@SpringBootApplication
@ComponentScan("com.hzh.liteflow_redis.service")
public class LiteFlowApplication {
    public static void main(String[] args) {
        SpringApplication.run(LiteFlowApplication.class,args);
    }
}