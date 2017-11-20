package com.sse.myhbase.client;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: Cai Shunda
 * @Description: 这个注解是用于Java对象field和HBase表列的对应关系
 *              一个java对象只能有0个或者1个@HBaseVersion，而且@HBaseVersion必须和@HBaseColumn配合使用
 * @Date: Created in 17:39 2017/11/20
 * @Modified by:
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HBaseVersion {

}
