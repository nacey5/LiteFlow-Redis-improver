
package com.hzh.liteflow_redis.listener.sub_pub;

import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author dahuang
 * @version : MyRedisSubscriber.java, v 0.1 2023-06-11 23:30 dahuang
 */
@Component
public class RuleChangeRedisSubscriber {
    @Autowired
    private RedissonClient redissonClient;

    public static final String TOPIC="rule";

    public void sendMessage(String channel, String message) {
        // 创建发布/订阅实例
        RTopic topic = redissonClient.getTopic(channel);
        // 订阅指定频道
        topic.publish(message);
    }
}