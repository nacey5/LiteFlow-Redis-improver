
package com.hzh.xml_rule.sync.executor;

import com.hzh.xml_rule.sync.XmlWriter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author dahuang
 * @version : ThreadPool.java, v 0.1 2023-06-13 00:08 dahuang
 * 暂时不用线程池
 */
public class ThreadPool {
    private static final ExecutorService POOLINSTANCE = Executors.newSingleThreadExecutor();

}