package com.hzh.all.util;

/**
 * @author dahuang
 * @version : AKUtil.java, v 0.1 2023-06-29 00:28 dahuang
 */

import java.util.HashMap;
import java.util.UUID;

public class AKUtil {

    public static final String AK_KEY = "ak";
    public static final String SK_KEY = "sk";

    public static HashMap<String, String> generateAk() {
        HashMap<String, String> map = new HashMap<>();
        String ak = generateUniqueKey();
        String sk = generateSecretKey();
        map.put(AK_KEY, ak);
        map.put(SK_KEY, sk);
        return map;
    }

    private static String generateUniqueKey() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-", "").toUpperCase();
    }

    private static String generateSecretKey() {
        // 生成Secret Key的逻辑，可以根据需求进行实现
        // 这里仅作为示例，生成一个随机字符串
        return UUID.randomUUID().toString();
    }
}
