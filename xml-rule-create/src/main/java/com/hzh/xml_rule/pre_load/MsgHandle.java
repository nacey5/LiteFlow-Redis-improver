
package com.hzh.xml_rule.pre_load;

import com.hzh.xml_rule.manager.BaseManager;
import lombok.RequiredArgsConstructor;

/**
 * @author dahuang
 * @version : MsgHandle.java, v 0.1 2023-06-14 14:05 dahuang
 */
@RequiredArgsConstructor
public class MsgHandle implements Runnable {

    private final String fullClassName;
    @Override
    public void run() {
        while (true) {
            BaseManager manager = ManagerFactory.getManager(fullClassName);
            manager.logic();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}