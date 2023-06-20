
package com.hzh.liteflow_redis.manager.impl;

import com.hzh.liteflow_redis.manager.RedisManager;
import com.hzh.liteflow_redis.service.RedisService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author dahuang
 * @version : RedisManagerImpl.java, v 0.1 2023-06-18 11:30 dahuang
 */

@Component
public class RedisManagerImpl implements RedisManager {
    @Resource
    private RedisService redisService;
    @Override
    public void setKeyValue(String key, String value) {
        redisService.setValue(key,value);
    }

    @Override
    public String getValueFromKey(String key) {
        return redisService.getValue(key);
    }

    @Override
    public List<String> getKeys() {
        return redisService.keys();
    }
}