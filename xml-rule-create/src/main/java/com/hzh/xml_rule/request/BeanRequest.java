
package com.hzh.xml_rule.request;

import com.hzh.all.request.CommonRequest;
import lombok.Data;

/**
 * @author dahuang
 * @version : BeanRequest.java, v 0.1 2023-06-18 15:28 dahuang
 */

@Data
public class BeanRequest implements CommonRequest {

    String beanName;
    @Override
    public void check() {

    }
}