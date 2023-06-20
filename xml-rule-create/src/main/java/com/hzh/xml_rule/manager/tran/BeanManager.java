
package com.hzh.xml_rule.manager.tran;

import java.util.List;

/**
 * @author dahuang
 * @version : BeanManager.java, v 0.1 2023-06-18 13:17 dahuang
 */
public interface BeanManager {

    Boolean registerBean(String fullClassName,String beanName);

    String  getBeanByBeanName(String bean);

    List<String> listAllBeanName();
}