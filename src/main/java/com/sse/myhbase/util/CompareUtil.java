package com.sse.myhbase.util;

/**
 * @author: Cai Shunda
 * @description:
 * @date: Created in 13:53 2018/3/2
 * @modified by:
 */
public class CompareUtil {

    /**
     * @Author: Cai Shunda
     * @Description: 将两个数对比 one<other =负数，one=other =0，one>other =正数
     * @Param:
     * @Date: 13:54 2018/3/2
     */
    public static int compare(Object one, Object other) {
        Util.checkNull(one);
        Util.checkNull(other);
        Comparable comp = (Comparable) one;
        return comp.compareTo(other);
    }
}
