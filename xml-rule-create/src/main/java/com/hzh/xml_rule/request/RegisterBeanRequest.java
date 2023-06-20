
package com.hzh.xml_rule.request;

import lombok.Data;

/**
 * @author dahuang
 * @version : RegisterBeanRequest.java, v 0.1 2023-06-18 15:29 dahuang
 */
@Data
public class RegisterBeanRequest extends BeanRequest{
    String fullClassName;
    @Override
    public void check() {
        super.check();
    }
}