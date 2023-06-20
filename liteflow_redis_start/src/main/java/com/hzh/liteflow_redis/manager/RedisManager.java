
package com.hzh.liteflow_redis.manager;

import java.util.List;

/**
 * @author dahuang
 * @version : RedisManager.java, v 0.1 2023-06-18 11:29 dahuang
 */
public interface RedisManager {

    void setKeyValue(String key,String value);

    String getValueFromKey(String key);

    List<String> getKeys();
}