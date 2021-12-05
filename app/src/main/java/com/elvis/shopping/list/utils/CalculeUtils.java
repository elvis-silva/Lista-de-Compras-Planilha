package com.elvis.shopping.list.utils;

import java.text.DecimalFormat;

public class CalculeUtils {


    public static String moneyFormat (float value) {
        DecimalFormat money = new DecimalFormat("0,000.00");

        String valueToString;
        if (money.format(value).startsWith("0") || money.format(value).startsWith("-0")) {
            money = new DecimalFormat("0.00");
            valueToString = money.format(value);
            return valueToString.replace(".",",");
        }
        valueToString = money.format(value).replace(",", ".");
        String firstPart = valueToString.substring(0, valueToString.length() - 3);
        String lastPart = valueToString.substring(valueToString.length() - 3, valueToString.length()).replace(".",",");

        return firstPart + lastPart;
    }

    public static String kiloFormat (float pValue) {
        String value = String.valueOf(pValue);
        if (String.valueOf(value.charAt(value.length() - 2)).equals(".") && String.valueOf(value.charAt(value.length() - 1)).equals("0")) {
            return value.substring(0, value.length() - 2);
        }
        DecimalFormat kilo = new DecimalFormat("0,000.000");
        String valueToString;
        if (kilo.format(pValue).startsWith("0") || kilo.format(pValue).startsWith("-0")) {
            kilo = new DecimalFormat("0.000");
            valueToString = kilo.format(pValue);
            return valueToString.replace(".",",");
        }
        valueToString = kilo.format(pValue).replace(",", ".");
        String firstPart = valueToString.substring(0, valueToString.length() - 4);
        String lastPart = valueToString.substring(valueToString.length() - 4, valueToString.length()).replace(".",",");
        return firstPart + lastPart;
    }

    public static float returnFloat (String pValue) {
        return Float.valueOf(pValue.replace(".","").replace(",","."));
    }
}
