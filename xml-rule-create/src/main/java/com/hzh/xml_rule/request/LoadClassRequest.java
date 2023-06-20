
package com.hzh.xml_rule.request;

import com.hzh.all.request.CommonRequest;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

/**
 * @author dahuang
 * @version : LoadClassRequest.java, v 0.1 2023-06-20 11:31 dahuang
 */

@Data
@Slf4j
public class LoadClassRequest implements CommonRequest {
    private String chainName;

    private String requestParam;

    @Override
    public void check() {
        if (StringUtils.isBlank(chainName)) {
            throw new RuntimeException("chainName must not be null");
        }
        if (StringUtils.isBlank(requestParam)) {
            this.setRequestParam("args");
        }
    }
}