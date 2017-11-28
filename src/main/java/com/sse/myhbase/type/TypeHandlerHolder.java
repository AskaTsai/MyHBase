package com.sse.myhbase.type;

import com.sse.myhbase.exception.MyHBaseException;
import com.sse.myhbase.util.ClassUtil;
import com.sse.myhbase.util.Util;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: Cai Shunda
 * @description: typehandler实例的holder
 * @date: Created in 21:55 2017/11/9
 * @modified by:
 */
public class TypeHandlerHolder {
    /**
     * typeHandler的类型 -> typeHandler实例
     */
    private static ConcurrentHashMap<String, TypeHandler> typeHandlerCache = new ConcurrentHashMap<>();

    /**
     * @author: Cai Shunda
     * @description: 通过typehandler的名字找到对应的typehandler实例
     * @date: 22:00 2017/11/9
     */
    public static TypeHandler findTypeHandler(String type) {
        Util.checkNull(type);

        if (typeHandlerCache.get(type) == null) {
            try {
                Class<?> c = ClassUtil.forName(type);
                typeHandlerCache.putIfAbsent(type, (TypeHandler) c.newInstance());
            } catch (Exception e) {
                throw new MyHBaseException(e);
            }
        }

        return typeHandlerCache.get(type);
    }
}
