
package com.hzh.xml_rule.controller.external_persistence;

import com.hzh.liteflow_redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dahuang
 * @version : RedisController.java, v 0.1 2023-06-04 19:05 dahuang
 */

@RestController
public class RedisController {

    @Autowired
    private RedisService redisService;

    public RedisController(RedisService redisService) {
        this.redisService = redisService;
    }


    @PostMapping("/redis/get/{key}")
    public String getValueFromRedis(@PathVariable String key) {
        return redisService.getValue(key);
    }

    @PostMapping("/redis/put/{key}")
    public void setValueInRedis(@PathVariable String key, @RequestBody String value) {
        redisService.setValue(key, value);
    }
}