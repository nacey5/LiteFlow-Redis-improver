
package com.hzh.loader;

/**
 * @author dahuang
 * @version : ClassLoadTest.java, v 0.1 2023-06-14 14:06 dahuang
 */
public class ClassLoadTest {
    public static void main(String[] args) {
        new Thread(new MsgHandle()).start();
    }
}