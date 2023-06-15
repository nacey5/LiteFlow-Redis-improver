
package com.hzh.liteflow_redis.controller2;

import com.hzh.liteflow_redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author dahuang
 * @version : DataCommonInsertController.java, v 0.1 2023-06-04 13:16 dahuang
 */

@RestController
public class DataCommonInsertController {

    @Autowired
    private RedisService redisService;

    public DataCommonInsertController(RedisService redisService) {
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