package com.sse.myhbase.util;

import com.sse.myhbase.exception.MyHBaseException;

import java.util.Collection;

/**
 * @author: Cai Shunda
 * @description:
 * @date: Created in 22:22 2017/11/11
 * @modified by:
 */
public class CollectionUtil {
    /**
     * @author: Cai Shunda
     * @description: 检查集合类是不是为null或者是大小为0
     * @date: 22:24 2017/11/11
     */
    public static void checkEmpty(Collection<?> collection) {
        if (collection == null || collection.size() ==0) {
            throw new MyHBaseException("Collection is null or empty");
        }
    }

    /**
     * @author: Cai Shunda
     * @description: 检查集合类是不是为null
     * @date: 22:25 2017/11/11
     */
    public static void checkNull(Collection<?> collection) {
        if (collection == null) {
            throw new MyHBaseException("Collection is null");
        }
    }
}
