
package com.hzh.xml_rule.service;

import java.util.List;

/**
 * @author dahuang
 * @version : IClassFlowService.java, v 0.1 2023-06-20 11:28 dahuang
 */
public interface IClassFlowService {

    String loadChainName(String chainName, String requestParam);

    List<String> loadAllChains();
}