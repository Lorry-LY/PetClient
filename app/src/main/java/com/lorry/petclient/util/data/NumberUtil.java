package com.lorry.petclient.util.data;

import android.content.Context;

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

    // 根据手机的分辨率从 dp 的单位 转成为 px(像素)
    public static int dip2px(Context context, float dpValue) {
        // 获取当前手机的像素密度（1个dp对应几个px）
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f); // 四舍五入取整
    }

    // 根据手机的分辨率从 px(像素) 的单位 转成为 dp
    public static int px2dip(Context context, float pxValue) {
        // 获取当前手机的像素密度（1个dp对应几个px）
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f); // 四舍五入取整
    }

}
