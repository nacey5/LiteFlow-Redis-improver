
package com.hzh.xml_rule.request;

import com.hzh.all.GrayScheduling;
import com.hzh.all.request.CommonRequest;
import lombok.Data;

/**
 * @author dahuang
 * @version : CodingRequest.java, v 0.1 2023-06-23 14:25 dahuang
 */

@Data
public class CodingRequest implements CommonRequest {

    String name;
    String desc;
    GrayScheduling tag;
    String coding;
    CodingRequest codingRequest;

    @Override
    public void check() {

    }
}