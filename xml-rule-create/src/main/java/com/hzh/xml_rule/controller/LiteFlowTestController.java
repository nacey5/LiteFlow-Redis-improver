
package com.hzh.xml_rule.controller;

import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.flow.LiteflowResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author dahuang
 * @version : LiteFlowTestController.java, v 0.1 2023-06-11 19:34 dahuang
 */
@RequestMapping("/lite")
@RestController
public class LiteFlowTestController {

    @Resource
    private FlowExecutor flowExecutor;

    @GetMapping("/testFlow")
    public void testFlow(){
        LiteflowResponse response = flowExecutor.execute2Resp("chain1", "arg");
        String message = response.getMessage();
        System.out.println(message);
    }
}