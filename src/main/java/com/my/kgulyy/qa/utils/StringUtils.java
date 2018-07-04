package com.my.kgulyy.qa.utils;

import java.io.UncheckedIOException;
import java.net.MalformedURLException;
import java.net.URL;

public class StringUtils {

    public static int getDigitsFromString(String str) {
        final String numberStr = str.replaceAll("\\D+", "");
        return Integer.parseInt(numberStr);
    }

    public static String createStringFromSymbol(int length, char symbol) {
        final StringBuilder stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            stringBuilder.append(symbol);
        }
        return stringBuilder.toString();
    }

    static URL getUrlFromString(String stringUrl) {
        try {
            return new URL(stringUrl);
        } catch (MalformedURLException e) {
            throw new UncheckedIOException(e);
        }
    }
}
