package com.sse.myhbase.type;

import com.sse.myhbase.core.Nullable;
import com.sse.myhbase.exception.MyHBaseException;
import com.sse.myhbase.util.Util;

/**
 * @author: Cai Shunda
 * @description: typeHandler骨架的实现,求同存异，把固定步骤实现，同时把不同的地方开放接口让子类实现
 * @date: Created in 22:20 2017/11/9
 * @modified by:
 */
public abstract class AbstractTypeHandler implements TypeHandler{

    /**
     * 判断这个handler处理的指定类型
     */
    abstract protected boolean aboutToHandler(Class<?> type);
    /**
     * 实现java对象转化成byte数组
     */
    abstract protected byte[] innerToBytes(Class<?> type, Object value);
    /**
     * 实现byte数组转化成java对象
     */
    abstract protected Object innertoObject(Class<?> type, byte[] bytes);

    @Override
    @Nullable
    public byte[] toBytes(Class<?> type, Object value) {
        Util.checkNull(type);

        if (!aboutToHandler(type)) {
            throw new MyHBaseException("wrong type to handle. type = " + type);
        }
        if (value == null) {
            return null;
        }
        return innerToBytes(type, value);
    }

    @Override
    @Nullable
    public Object toObject(Class<?> type, byte[] bytes) {
        Util.checkNull(type);

        if (!aboutToHandler(type)) {
            throw new MyHBaseException("wrong type to handle. type = " + type);
        }
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        return innertoObject(type, bytes);
    }
}
