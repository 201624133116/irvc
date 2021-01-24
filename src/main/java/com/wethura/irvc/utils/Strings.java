package com.wethura.irvc.utils;

/**
 * @author wethura
 * @date 2021/1/25 上午2:09
 */
public class Strings {
    public static boolean isNullOrEmpty(String str) {
        return str == null || "".equals(str);
    }

    public static String getOrDefault(String str, String defaultStr) {
        return isNullOrEmpty(str) ? defaultStr : str;
    }
}
