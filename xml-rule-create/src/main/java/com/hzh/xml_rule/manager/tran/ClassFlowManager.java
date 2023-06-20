
package com.hzh.xml_rule.manager.tran;

import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author dahuang
 * @version : ClassFlowManager.java, v 0.1 2023-06-20 11:25 dahuang
 */
public interface ClassFlowManager {

    Boolean loadChainName(String chainName,String requestParam);

    List<String> loadAllChains();
}