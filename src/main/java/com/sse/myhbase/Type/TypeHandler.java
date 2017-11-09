package com.sse.myhbase.Type;

import com.sse.myhbase.core.Nullable;

/**
 * @Author: Cai Shunda
 * @Description: java对象和HBase bytes列值之间的来回转换
 * @Date: Created in 22:52 2017/11/7
 * @Modified by:
 */
public interface TypeHandler {
    /**
     * @Author: Cai Shunda
     * @Description: 把java对象转化成bytes列值
     * @Date: 22:23 2017/11/9
     */
    @Nullable
    public byte[] toBytes(Class<?> type, @Nullable Object value);


    /**
     * @Author: Cai Shunda
     * @Description: 把bytes列值转化成java对象
     * @Date: 22:24 2017/11/9
     */
    @Nullable
    public Object toObject(Class<?> type, @Nullable byte[] bytes);
}
