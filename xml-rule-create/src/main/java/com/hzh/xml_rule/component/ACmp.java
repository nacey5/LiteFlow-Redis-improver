
package com.hzh.xml_rule.component;

import com.yomahub.liteflow.core.NodeComponent;
import org.springframework.stereotype.Component;

/**
 * @author dahuang
 * @version : ACmp.java, v 0.1 2023-06-11 14:38 dahuang
 */
@Component("a")
public class ACmp extends NodeComponent {

    @Override
    public void process() {
        //do your business
        System.out.println("a");
    }
}