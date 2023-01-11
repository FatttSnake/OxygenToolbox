package com.fatapp.oxygentoolbox.util;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public class HexConversionUtils {
    private static int handleLetter(String letter) {
        return Integer.parseInt(letter, 36);
    }

    private static String handleNumber(int number) {
        return Integer.toString(number, 36);
    }

    public static String getRegExp(int base) {
        String regExp;
        if (base <= 10) {
            regExp = "^[+\\-]?[0-" + (base - 1) + "]*(?:[0-" + (base - 1) + "][.][0-" + (base - 1) + "]*)?";
        } else {
            String letter = Integer.toString(base - 1, 36);
            regExp = "^[+\\-]?[0-9a-" + letter + "]*(?:[0-9a-" + letter + "][.][0-9a-" + letter + "]*)?";
        }
        return regExp;
    }

    public static String getStrictRegExp(int base) {
        String regExp;
        if (base <= 10) {
            regExp = "^[+\\-]?[0-" + (base - 1) + "]+(?:[.][0-" + (base - 1) + "]+)?$";
        } else {
            String letter = Integer.toString(base - 1, 36);
            regExp = "^[+\\-]?[0-9a-" + letter + "]+(?:[.][0-9a-" + letter + "]+)?$";
        }
        return regExp;
    }

    public static boolean checkNumber(String number, int base) {
        return Pattern.matches(getStrictRegExp(base), number);
    }

    private static String baseToDecimal_(String number, int base) {
        if (base == 10) {
            return number;
        }
        BigDecimal result = new BigDecimal(0);
        String[] data = new StringBuilder(number).reverse().toString().split("");
        for (int i = 0; i < data.length; i++) {
            result = result.add(BigDecimal.valueOf(handleLetter(data[i]) * Math.pow(base, i)));
        }
        return result.toString().split("\\.")[0];
    }

    private static String decimalToBase_(String decimal, int base) {
        if (base == 10) {
            return decimal;
        }
        StringBuilder result = new StringBuilder();
        BigDecimal quotient = new BigDecimal(decimal);
        while (quotient.compareTo(new BigDecimal(base)) > -1) {
            BigDecimal[] divideAndRemainder = quotient.divideAndRemainder(new BigDecimal(base));
            quotient = divideAndRemainder[0];
            BigDecimal remainder = divideAndRemainder[1];
            result.insert(0, handleNumber(remainder.intValue()));
        }
        result.insert(0, handleNumber(quotient.intValue()));
        return result.toString();
    }

    private static String _baseToDecimal(String number, int base) {
        if (base == 10) {
            return number;
        }
        BigDecimal result = new BigDecimal(0);
        String[] data = number.split("");
        for (int i = 0; i < data.length; i++) {
            result = result.add(BigDecimal.valueOf(handleLetter(data[i]) * Math.pow(base, -1 * (i + 1))));
        }
        return result.toString().split("\\.")[1];
    }

    private static String _decimalToBase(String decimal, int base) {
        if (base == 10) {
            return decimal;
        }
        BigDecimal bigDecimal = new BigDecimal("0." + decimal);
        StringBuilder result = new StringBuilder();

        while (bigDecimal.compareTo(new BigDecimal(0)) > 0 && result.length() < 50) {
            bigDecimal = bigDecimal.multiply(new BigDecimal(base));
            if (bigDecimal.toString().contains("E")) {
                break;
            }
            if (bigDecimal.toString().contains(".99999")) {
                result.append(handleNumber(Integer.parseInt(bigDecimal.toString().split("\\.")[0]) + 1));
                break;
            }
            result.append(handleNumber(Integer.parseInt(bigDecimal.toString().split("\\.")[0])));
            bigDecimal = new BigDecimal("0." + bigDecimal.toString().split("\\.")[1]);
        }
        return result.toString();
    }

    public static String baseToDecimal(String number, int base) throws Exception{
        if (!checkNumber(number, base)) {
            throw new Exception("Illegal number");
        }
        StringBuilder result = new StringBuilder();

        String sign = "";
        if (number.charAt(0) == '-' || number.charAt(0) == '+') {
            sign = number.substring(0, 1);
            number = number.substring(1);
        }

        if (!number.contains(".")) {
            result.append(baseToDecimal_(number, base));
        } else {
            String[] data = number.split("\\.");
            result.append(baseToDecimal_(data[0], base));
            result.append(".");
            result.append(_baseToDecimal(data[1], base));
        }

        result.insert(0, sign);

        return result.toString();
    }

    public static String decimalToBase(String number, int base) {
        StringBuilder result = new StringBuilder();

        String sign = "";
        if (number.charAt(0) == '-' || number.charAt(0) == '+') {
            sign = number.substring(0, 1);
            number = number.substring(1);
        }

        if (!number.contains(".")) {
            result.append(decimalToBase_(number, base));
        } else {
            String[] data = number.split("\\.");
            result.append(decimalToBase_(data[0], base));
            result.append(".");
            result.append(_decimalToBase(data[1], base));
        }

        result.insert(0, sign);

        return result.toString();
    }
}