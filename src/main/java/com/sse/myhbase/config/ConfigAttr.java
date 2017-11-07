package com.sse.myhbase.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: Cai Shunda
 * @Description: 用于指示对应java属性是配置属性的标志
 * @Date: Created in 20:30 2017/11/2
 * @Modified by:
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.SOURCE)
public @interface ConfigAttr {
}
