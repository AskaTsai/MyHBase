package com.sse.myhbase.client;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: Cai Shunda
 * @description:  java对象field和HBase列的对应注解
 * @date: Created in 17:18 2017/11/20
 * @modified by:
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HBaseColumn {
    /**
     * 列族
     */
    public String family() default "";

    /**
     * 列名
     */
    public String qualifier();
}
