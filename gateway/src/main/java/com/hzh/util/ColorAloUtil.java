
package com.hzh.util;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author dahuang
 * @version : ColorAloUtil.java, v 0.1 2023-06-26 21:43 dahuang
 */
public class ColorAloUtil {
    public static String selectColor(String[] colorArr, int[] weightArr) {
        int length = colorArr.length;
        boolean sameWeight = true;
        int totalWeight = 0;
        for (int i = 0; i < length; i++) {
            int weight = weightArr[i];
            totalWeight += weight;
            if (sameWeight && totalWeight != weight * (i + 1)) {
                sameWeight = false;
            }
        }
        if (totalWeight > 0 && !sameWeight) {
            int offset = ThreadLocalRandom.current().nextInt(totalWeight);
            System.out.println("offset:" + offset);
            for (int i = 0; i < length; i++) {
                if (offset < weightArr[i]) {
                    return colorArr[i];
                }
            }
        }
        return colorArr[ThreadLocalRandom.current().nextInt(length)];
    }
}