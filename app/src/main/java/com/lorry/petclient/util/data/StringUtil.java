package com.lorry.petclient.util.data;

public class StringUtil {

    public static String format(String src, int length) {
        if (src.length() > length) {
            return src.subSequence(0, length) + "...";
        } else {
            return src;
        }
    }

}
