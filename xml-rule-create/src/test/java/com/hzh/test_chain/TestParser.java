
package com.hzh.test_chain;

import com.hzh.liteflow_redis.service.RedisService;
import com.hzh.xml_rule.RuleApplication;
import com.hzh.xml_rule.parse.MyParser;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @author dahuang
 * @version : Testparser.java, v 0.1 2023-06-11 16:20 dahuang
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RuleApplication.class)
@WebAppConfiguration
@Slf4j
public class TestParser {

    @Resource
    private RedisService redisService;

    @Resource
    private MyParser myParser;

    @Test
    public void parseR() throws Exception{
        myParser.parseMain(Arrays.asList("config/flow.el.xml"));
    }

    @Test
    public void parseRFirst() throws Exception{
        //先去本地文件查找是否存在rule.xml文件，有的话直接解析，出现错误的话再去redis中查找是否有当前的xml文件再去生成并且读取
        myParser.parseRedis();
    }
}