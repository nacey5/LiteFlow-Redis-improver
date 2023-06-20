
package com.hzh.xml_rule.service.impl;

import com.hzh.xml_rule.service.IBeanService;
import com.yomahub.liteflow.spring.ComponentScanner;
import org.springframework.stereotype.Service;

/**
 * @author dahuang
 * @version : BeanService.java, v 0.1 2023-06-18 13:28 dahuang
 */
@Service
public class BeanService implements IBeanService {
    @Override
    public void scanIntoSpringboot(Object o,String beanName){
        ComponentScanner componentScanner = new ComponentScanner();
        componentScanner.postProcessBeforeInitialization(o, beanName);
        componentScanner.postProcessAfterInitialization(o, beanName);
    }
}