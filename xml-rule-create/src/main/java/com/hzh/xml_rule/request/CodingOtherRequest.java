
package com.hzh.xml_rule.request;

import com.hzh.all.request.CommonRequest;
import lombok.Data;

/**
 * @author dahuang
 * @version : CodingOtherRequest.java, v 0.1 2023-06-23 14:27 dahuang
 * 一个特殊的对象，初始目的是为了给库和命令做仿真，目前搁置，但是预留类
 */
@Data
public class CodingOtherRequest implements CommonRequest {
    @Override
    public void check() {

    }
}