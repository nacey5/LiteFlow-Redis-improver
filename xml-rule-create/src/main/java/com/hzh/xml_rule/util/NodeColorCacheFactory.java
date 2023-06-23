
package com.hzh.xml_rule.util;

import com.hzh.all.GrayScheduling;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dahuang
 * @version : NodeColorCache.java, v 0.1 2023-06-23 14:33 dahuang
 */
public class NodeColorCacheFactory{

    public static Map<GrayScheduling, String> buildNewCacheMap(){
        Map<GrayScheduling, String> map=new HashMap<>();
        initialize(map);
        return map;
    }


    private static void initialize(Map<GrayScheduling, String>  map) {
        map.put(GrayScheduling.DEFAULT,"");
        map.put(GrayScheduling.BLUE,"");
        map.put(GrayScheduling.GREEN,"");
    }

}