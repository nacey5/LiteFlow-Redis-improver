package com.hzh.liteflow_redis.contrtroller;

import com.hzh.liteflow_redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dahuang
 * @version : ExampleController.java, v 0.1 2023-06-04 12:03 dahuang
 */

@RestController
public class ExampleController {
    @Autowired
    private  RedisService redisService;

    public ExampleController(RedisService redisService) {
        this.redisService = redisService;
    }

    @GetMapping("/redis/{key}")
    public String getValueFromRedis(@PathVariable String key) {
        return redisService.getValue(key);
    }

    @GetMapping("/redis/{key}/{value}")
    public void setValueInRedis(@PathVariable String key, @PathVariable String value) {
        redisService.setValue(key, value);
    }
}