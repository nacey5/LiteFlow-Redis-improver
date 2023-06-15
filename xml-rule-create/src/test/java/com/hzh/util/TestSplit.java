
package com.hzh.util;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author dahuang
 * @version : TestSplit.java, v 0.1 2023-06-14 21:55 dahuang
 */
public class TestSplit {

    @Test
    public void testSplitFullClassName(){
        String fullPackageName = "com.hzh.xml_rule.Hello";
        String[] parts = fullPackageName.split("\\.");
        String packagePath = String.join("/", Arrays.copyOfRange(parts, 0, parts.length - 1));
        String className = parts[parts.length - 1];

        System.out.println("Package Path: " + packagePath);
        System.out.println("Class Name: " + className);
    }
}