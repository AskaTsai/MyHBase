package com.sse.myhbase.type;

import com.sse.myhbase.core.Nullable;

/**
 * @author: Cai Shunda
 * @description: java对象和HBase bytes列值之间的来回转换
 * @date: Created in 22:52 2017/11/7
 * @modified by:
 */
public interface TypeHandler {
    /**
     * @author: Cai Shunda
     * @description: 把java对象转化成bytes列值
     * @date: 22:23 2017/11/9
     */
    @Nullable
    public byte[] toBytes(Class<?> type, @Nullable Object value);


    /**
     * @author: Cai Shunda
     * @description: 把bytes列值转化成java对象
     * @date: 22:24 2017/11/9
     */
    @Nullable
    public Object toObject(Class<?> type, @Nullable byte[] bytes);
}
