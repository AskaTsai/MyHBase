package com.sse.myhbase.client;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: Cai Shunda
 * @Description: 表示简单Java对象能与HBase映射
 * @Date: Created in 21:43 2017/11/19
 * @Modified by:
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface HBaseTable {
    /**
     * @Author: Cai Shunda
     * @Description: 该Pojo对应的表默认的列族，这个属性会被@HBaseColumn这个注解的family覆盖
     * @Param:
     * @Date: 21:47 2017/11/19
     */
    public String defaultFamily() default "";

}
