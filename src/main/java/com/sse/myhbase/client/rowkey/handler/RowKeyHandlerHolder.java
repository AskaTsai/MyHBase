package com.sse.myhbase.client.rowkey.handler;

import com.sse.myhbase.exception.MyHBaseException;
import com.sse.myhbase.util.ClassUtil;
import com.sse.myhbase.util.Util;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: Cai Shunda
 * @Description:
 * @Date: Created in 16:10 2017/11/9
 * @Modified by:
 */
public class RowKeyHandlerHolder {
    /**
     * handler缓存， RowKeyHandler的类型-> RowKeyHandler的实例
     */
    private static ConcurrentHashMap<String, RowKeyHandler> cache = new ConcurrentHashMap<>();


    /**
     * @Author: Cai Shunda
     * @Description: 通过类名找到行键handler的实例
     * @Date: 16:38 2017/11/9
     */
    public static RowKeyHandler findRowKeyHandler(String type) {
        Util.checkNull(type);

        if (cache.get(type) == null) {
            try {
                Class<?> c = ClassUtil.forName(type);
                cache.putIfAbsent(type, (RowKeyHandler) c.newInstance());
            } catch (Exception e) {
               throw new MyHBaseException("can not get instance of " + type , e);
            }
        }
        return cache.get(type);
    }
}
