package com.sse.myhbase.util;

/**
 * @Author: Cai Shunda
 * @Description: 字符相关
 * @Date: Created in 12:19 2017/11/7
 * @Modified by:
 */
public class StringUtil {
    /**
     * @Author: Cai Shunda
     * @Description: 检查字符串是否是null或者""
     * @Date: 12:21 2017/11/7
     */
    public static boolean isEmptyString(String str) {
        return str == null ||str.isEmpty();
    }

    /**
     * @Author: Cai Shunda
     * @Description: 检查字符串不为null且不为""
     * @Date: 11:12 2017/11/8
     */
    public static boolean isNotEmptyString(String str) {return !isEmptyString(str);}
}
