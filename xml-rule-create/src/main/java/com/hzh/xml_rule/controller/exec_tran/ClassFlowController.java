
package com.hzh.xml_rule.controller.exec_tran;

import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.flow.LiteflowResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author dahuang
 * @version : ClassFlowController.java, v 0.1 2023-06-11 14:44 dahuang
 */

@RestController
public class ClassFlowController {

    @Resource
    private FlowExecutor flowExecutor;


    @GetMapping("/testConfig")
    public void testConfig() {
        LiteflowResponse response = flowExecutor.execute2Resp("chain1", "arg");
    }
}