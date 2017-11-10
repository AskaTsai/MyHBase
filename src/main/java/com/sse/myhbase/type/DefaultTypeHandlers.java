package com.sse.myhbase.type;

import com.sse.myhbase.type.handler.*;
import com.sse.myhbase.util.ClassUtil;
import com.sse.myhbase.util.Util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Cai Shunda
 * @Description:
 * @Date: Created in 21:12 2017/11/9
 * @Modified by:
 */
public class DefaultTypeHandlers {
    private static EnumHandler enumHandler = new EnumHandler();
    private static Map<Class<?>, TypeHandler> defaultHandlers = new HashMap<Class<?>, TypeHandler>();
    static {
        defaultHandlers.put(String.class, new StringHandler());
        defaultHandlers.put(Date.class, new DateHandler());
        defaultHandlers.put(Boolean.class, new BooleanHandler());
        defaultHandlers.put(Character.class, new CharacterHandler());
        defaultHandlers.put(Byte.class, new ByteHandler());
        defaultHandlers.put(Short.class, new ShortHandler());
        defaultHandlers.put(Integer.class, new IntegerHandler());
        defaultHandlers.put(Long.class, new LongHandler());
        defaultHandlers.put(Float.class, new FloatHandler());
        defaultHandlers.put(Double.class, new DoubleHandler());
    }
    public static TypeHandler findDefaultTypeHandler(Class<?> type) {
        Util.checkNull(type);

        type = ClassUtil.tryConvertToBoxClass(type);
        if (type.isEnum()) {
            return enumHandler;
        }

        return defaultHandlers.get(type);

    }
}
