package com.sse.myhbase.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: Cai Shunda
 * @description:
 * @date: Created in 19:58 2018/3/1
 * @modified by:
 */
public class DateUtil {

    public static final String MillSecondFormat = "yyyy-MM-dd_HH:mm:ss:SSS";
    public static final String SecondFormat = "yyyy-MM-dd_HH:mm:ss";
    public static final String MinuteFormat = "yyyy-MM-dd_HH:mm";
    public static final String HourFormat = "yyyy-MM-dd_HH";
    public static final String DayFormat = "yyyy-MM-dd";

    /**
     * @Author: Cai Shunda
     * @Description: 把str安装指定格式转化成date，如果转化失败的话就返回null
     * @Param:
     * @Date: 20:04 2018/3/1
     */
    public static Date parse(String str, String format) {
        Util.checkEmptyString(str);
        Util.checkEmptyString(format);

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            return simpleDateFormat.parse(str);
        } catch (ParseException e) {
            //
        }
        return null;

    }
}
