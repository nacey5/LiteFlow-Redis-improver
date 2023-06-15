
package com.hzh.loader;

import java.time.LocalTime;

/**
 * @author dahuang
 * @version : MyManager.java, v 0.1 2023-06-14 13:41 dahuang
 */
public class MyManager implements BaseManager {

    @Override
    public void logic() {
        System.out.println(LocalTime.now() + ": Java类的热加载");
    }
}