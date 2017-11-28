package com.sse.myhbase.util;

import com.sse.myhbase.exception.MyHBaseException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: Cai Shunda
 * @description:
 * @date: Created in 16:26 2017/11/9
 * @modified by:
 */
public class ClassUtil {
    private static Map<String, Class<?>> nativeClassMap = new HashMap<String, Class<?>>();
    private static Map<String, Class<?>>   simpleClassMap = new HashMap<String, Class<?>>();
    private static Map<Class<?>, Class<?>> boxTypeMap     = new HashMap<Class<?>, Class<?>>();


    static {
        nativeClassMap.put("byte", byte.class);
        nativeClassMap.put("short", short.class);
        nativeClassMap.put("int", int.class);
        nativeClassMap.put("long", long.class);
        nativeClassMap.put("char", char.class);
        nativeClassMap.put("float", float.class);
        nativeClassMap.put("double", double.class);
        nativeClassMap.put("boolean", boolean.class);

        simpleClassMap.put("Byte", Byte.class);
        simpleClassMap.put("Short", Short.class);
        simpleClassMap.put("Int", Integer.class);
        simpleClassMap.put("Long", Long.class);
        simpleClassMap.put("Char", Character.class);
        simpleClassMap.put("Float", Float.class);
        simpleClassMap.put("Double", Double.class);
        simpleClassMap.put("Boolean", Boolean.class);

        simpleClassMap.put("string", String.class);
        simpleClassMap.put("String", String.class);
        simpleClassMap.put("date", Date.class);
        simpleClassMap.put("Date", Date.class);

        boxTypeMap.put(byte.class, Byte.class);
        boxTypeMap.put(short.class, Short.class);
        boxTypeMap.put(int.class, Integer.class);
        boxTypeMap.put(long.class, Long.class);
        boxTypeMap.put(char.class, Character.class);
        boxTypeMap.put(float.class, Float.class);
        boxTypeMap.put(double.class, Double.class);
        boxTypeMap.put(boolean.class, Boolean.class);
    }

    /**
     * @author: Cai Shunda
     * @description: 通过类名找到类
     * 原生类int/Int, boolean/Boolean.
     * 包装类, such as string/String,date/Date.
     * 剩下的类的类名要完整
     * @date: 16:34 2017/11/9
     */
    public static Class<?> forName(String className) {
        Util.checkEmptyString(className);

        if (nativeClassMap.containsKey(className)) {
            return nativeClassMap.get(className);
        }

        if (simpleClassMap.containsKey(className)) {
            return simpleClassMap.get(className);
        }

        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new MyHBaseException(e);
        }
    }

    /**
     * @author: Cai Shunda
     * @description: 如果c能够转化成封装类就转成封装类返回，否则返回c
     * @date: 21:22 2017/11/9
     */
    public static Class<?> tryConvertToBoxClass(Class<?> c){
        Util.checkNull(c);

        if (boxTypeMap.containsKey(c)) {
            return boxTypeMap.get(c);
        }
        return c;
    }
}
