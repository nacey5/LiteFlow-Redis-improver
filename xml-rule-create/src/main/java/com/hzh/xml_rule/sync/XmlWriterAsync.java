
package com.hzh.xml_rule.sync;

import java.util.concurrent.CompletableFuture;

/**
 * @author dahuang
 * @version : XmlWriterAsync.java, v 0.1 2023-06-13 00:14 dahuang
 */
public class XmlWriterAsync {
    public static void writeAsync(){
        CompletableFuture.runAsync(new XmlWriter());
    }
}