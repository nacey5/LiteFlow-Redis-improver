
package com.hzh.xml_rule.request;

import com.hzh.all.request.CommonRequest;
import lombok.Data;
/**
 * @author dahuang
 * @version : CodeRequest.java, v 0.1 2023-06-18 15:46 dahuang
 */
@Data
public class CodeRequest implements CommonRequest {
    String fullClassName;
    String javaCode;

    @Override
    public void check() {

    }
}