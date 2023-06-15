
package com.hzh.xml_rule.controller_rule_exchange;

import com.hzh.xml_rule.parse.MyParser;
import com.hzh.xml_rule.pre_load.MsgHandle;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @author dahuang
 * @version : RuleChange.java, v 0.1 2023-06-11 19:50 dahuang
 */
@RestController
@RequestMapping("change")
public class RuleChange {

    @Resource
    private MyParser myParser;

    @GetMapping("/change")
    public void changeRule() throws Exception {
        myParser.parseRedis();
    }

    @GetMapping("/pre_load")
    public void preLoad(){
        new Thread(new MsgHandle()).start();
    }
}