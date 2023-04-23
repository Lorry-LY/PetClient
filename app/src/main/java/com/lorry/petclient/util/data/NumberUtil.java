package com.lorry.petclient.util.data;

public class NumberUtil {

    public static String format(Number number) {
        String str = String.format("%d", number);
        if (str.length() > 8) {
            long num = number.longValue();
            number = num / 100000000F;
            return String.format("%.1f亿", number.floatValue());
        } else if (str.length() > 4) {
            long num = number.longValue();
            number = num / 10000F;
            return String.format("%.1f万", number.floatValue());
        } else {
            return str;
        }
    }

}
