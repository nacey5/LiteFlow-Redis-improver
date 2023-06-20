
package com.hzh.xml_rule.manager.tran.impl;

import com.hzh.xml_rule.manager.tran.ClassFlowManager;
import com.hzh.xml_rule.service.IClassFlowService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author dahuang
 * @version : ClassFlowManagerImpl.java, v 0.1 2023-06-20 11:26 dahuang
 */

@Component
@Slf4j
public class ClassFlowManagerImpl implements ClassFlowManager {

    @Resource
    private IClassFlowService classFlowService;

    @Override
    public Boolean loadChainName(String chainName, String requestParam) {
        String chain = classFlowService.loadChainName(chainName, requestParam);
        if (StringUtils.isBlank(chain)) {
            log.warn("loadChain:{} failed", chain);
            return false;
        }
        log.info("loadChain:{} success", chain);
        return true;
    }

    @Override
    public List<String> loadAllChains() {
        log.info("start load all effective chain");
        return classFlowService.loadAllChains();
    }
}