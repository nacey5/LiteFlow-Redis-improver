
package com.hzh.xml_rule.controller.rule;

import com.hzh.xml_rule.parse.RuleParser;
import com.hzh.xml_rule.pre_load.MsgHandle;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author dahuang
 * @version : RuleChange.java, v 0.1 2023-06-11 19:50 dahuang
 */
@RestController
@RequestMapping("change")
public class RuleChange {

    @Resource
    private RuleParser ruleParser;

    @GetMapping("/change")
    public void changeRule() throws Exception {
        ruleParser.parseRedis();
    }

    @GetMapping("/pre_load/{fullClassName}")
    public void preLoad(@PathVariable String fullClassName){
        new Thread(new MsgHandle(fullClassName)).start();
    }
}