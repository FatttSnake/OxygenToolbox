package com.fatapp.oxygentoolbox.tools.unicode;

import java.util.StringJoiner;

public class Utils {
    public static String stringToAscii(String value) {
        StringJoiner stringJoiner = new StringJoiner(";&#", "&#", ";");
        char[] chars = value.toCharArray();
        for (char aChar : chars) {
            stringJoiner.add(String.valueOf((int) aChar));
        }
        return stringJoiner.toString();
    }

    public static String asciiToString(String value) throws Exception {
        if (!value.startsWith("&#") || !value.endsWith(";")) {
            throw new Exception();
        }

        StringBuilder stringBuffer = new StringBuilder();
        value = value.substring(2);
        value = value.substring(0, value.length()-1);
        String[] chars = value.split(";&#");
        for (String aChar : chars) {
            stringBuffer.append((char) Integer.parseInt(aChar));
        }
        return stringBuffer.toString();
    }

    public static String stringToUnicode(String value) {
        StringJoiner stringJoiner = new StringJoiner("\\u", "\\u", "");
        char[] chars = value.toCharArray();
        for (char aChar : chars) {
            stringJoiner.add(Integer.toHexString(aChar));
        }
        return stringJoiner.toString();
    }

    public static String unicodeToString(String value) throws Exception {
        if (!value.startsWith("\\u")) {
            throw new Exception();
        }

        StringBuilder stringBuffer = new StringBuilder();
        value = value.substring(2);
        String[] chars = value.split("\\\\u");
        for (String aChar : chars) {
            stringBuffer.append((char) Integer.parseInt(aChar, 16));
        }
        return stringBuffer.toString();
    }
}
