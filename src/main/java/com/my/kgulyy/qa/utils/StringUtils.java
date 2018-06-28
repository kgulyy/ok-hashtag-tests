package com.my.kgulyy.qa.utils;

public class StringUtils {

    public static int getDigitsFromString(String str) {
        final String numberStr = str.replaceAll("\\D+", "");
        return Integer.parseInt(numberStr);
    }
}
