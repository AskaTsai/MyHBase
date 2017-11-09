package com.sse.myhbase.Type;

import com.sse.myhbase.exception.MyHBaseException;
import com.sse.myhbase.util.ClassUtil;
import com.sse.myhbase.util.Util;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: Cai Shunda
 * @Description: typehandler实例的holder
 * @Date: Created in 21:55 2017/11/9
 * @Modified by:
 */
public class TypeHandlerHolder {
    /**
     * typeHandler的类型 -> typeHandler实例
     */
    private static ConcurrentHashMap<String, TypeHandler> typeHandlerCache = new ConcurrentHashMap<>();

    /**
     * @Author: Cai Shunda
     * @Description: 通过typehandler的名字找到对应的typehandler实例
     * @Date: 22:00 2017/11/9
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
