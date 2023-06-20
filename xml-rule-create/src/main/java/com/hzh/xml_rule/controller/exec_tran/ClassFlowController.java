package com.hzh.xml_rule.controller.exec_tran;

import com.hzh.all.annotation.CustomMethodValidation;
import com.hzh.xml_rule.manager.tran.ClassFlowManager;
import com.hzh.xml_rule.request.LoadClassRequest;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author dahuang
 * @version : ClassFlowController.java, v 0.1 2023-06-11 14:44 dahuang
 */

@RestController
@RequestMapping("/chain")
public class ClassFlowController {

    @Resource
    private ClassFlowManager classFlowManager;

    @PostMapping("/loadChain")
    @CustomMethodValidation
    public Boolean loadChainName(@RequestBody LoadClassRequest loadClassRequest) {
        loadClassRequest.check();
        return classFlowManager.loadChainName(loadClassRequest.getChainName(), loadClassRequest.getRequestParam());
    }

    @GetMapping("/loadAllChainFromRedis")
    public List<String> loadAllChains() {
        return classFlowManager.loadAllChains();
    }
}