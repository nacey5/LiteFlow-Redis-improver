
package com.hzh.liteflow_redis.service;

import com.hzh.liteflow_redis.listener.sub_pub.RuleChangeRedisSubscriber;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dahuang
 * @version : RedisService.java, v 0.1 2023-06-04 01:24 dahuang
 */
@Service
@Slf4j
public class RedisService {

    @Resource
    private RedissonClient redissonClient;
    @Resource
    private RuleChangeRedisSubscriber ruleChangeRedisSubscriber;


    public RedisService(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    public void setValue(String key, String value) {
        redissonClient.getBucket(key).set(value);
        ruleChangeRedisSubscriber.sendMessage(RuleChangeRedisSubscriber.TOPIC,key+"#"+value);
        log.info("success");
    }

    public String getValue(String key) {
        return (String)redissonClient.getBucket(key).get();
    }

    public List<String> keys() {
        return redissonClient.getKeys().getKeysStream().collect(Collectors.toList());
    }
}