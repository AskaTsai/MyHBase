package com.sse.myhbase.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: Cai Shunda
 * @description: 表示方法的参数和返回值，以及类属性可以为空的标志
 * @date: Created in 20:39 2017/11/2
 * @modified by:
 */
@Target({ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.SOURCE)
public @interface Nullable {
}
