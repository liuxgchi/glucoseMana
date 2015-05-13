package com.common.library.util;

import java.text.NumberFormat;

/**
 * Created by xing on 2015/4/4.
 */
public class NumberUtil {
    /**
     *得到百分比
     * @param s1
     * @param s2
     * @return
     */
    public static int getPercent(String s1,String s2){
        float f1 = Float.parseFloat(s1);
        float f2 = Float.parseFloat(s2);
        float percent = f1/f2*100;
        NumberFormat numberFormat = NumberFormat.getInstance();
        // 设置精确到小数点后2位
        numberFormat.setMaximumFractionDigits(0);
        return Integer.parseInt(numberFormat.format(percent));
    }

    /**
     * 得到百分比
     * @param n1
     * @param n2
     * @return
     */
    public static int getPercent(int n1,int n2){
       return getPercent(String.valueOf(n1),String.valueOf(n2));
    }


}
