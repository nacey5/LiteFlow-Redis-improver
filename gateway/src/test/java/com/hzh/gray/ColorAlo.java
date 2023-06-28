
package com.hzh.gray;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author dahuang
 * @version : ColorAlo.java, v 0.1 2023-06-26 21:32 dahuang
 */
public class ColorAlo {
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

    //测试代码
    public static void main(String[] args) {
        String[] colorArr = new String[] {"GREEN", "BLUE"};
        int[] weightArr = new int[] {100, 50};
        for (int i = 0; i < 20; i++) {
            System.out.println(selectColor(colorArr, weightArr));
        }
    }
}