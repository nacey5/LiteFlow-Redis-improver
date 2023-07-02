
package com.hzh.xml_rule.config;

import com.hzh.xml_rule.sub_pub.RuleMessageListener;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

import static com.hzh.liteflow_redis.listener.sub_pub.RuleChangeRedisSubscriber.TOPIC;

/**
 * @author dahuang
 * @version : RedissonConfig.java, v 0.1 2023-06-11 23:45 dahuang
 */
@Configuration
public class RedissonConfig {

    private final RuleMessageListener messageReceiver;

    private final RedissonClient redissonClient;

    public RedissonConfig(@Autowired RuleMessageListener messageReceiver, @Qualifier("redissonClient1") RedissonClient redissonClient) {
        this.messageReceiver = messageReceiver;
        this.redissonClient = redissonClient;
    }

    @Bean
    public void registerMessageListener() {
        RTopic topic = redissonClient.getTopic(TOPIC);
        topic.addListener(String.class,messageReceiver);
    }
}






