
package com.hzh.xml_rule.pre_load;

import com.hzh.xml_rule.manager.BaseManager;

/**
 * @author dahuang
 * @version : MsgHandle.java, v 0.1 2023-06-14 14:05 dahuang
 */
public class MsgHandle implements Runnable {
    @Override
    public void run() {
        while (true) {
            BaseManager manager = ManagerFactory.getManager(ManagerFactory.MY_MANAGER);
            manager.logic();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}