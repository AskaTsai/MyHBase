package com.sse.myhbase.type.handler;

import com.sse.myhbase.type.AbstractTypeHandler;
import com.sse.myhbase.util.Util;

/**
 * @author: Cai Shunda
 * @description:
 * @date: Created in 20:36 2017/11/10
 * @modified by:
 */
public class ByteHandler extends AbstractTypeHandler {
    @Override
    protected boolean aboutToHandler(Class<?> type) {
        return type == byte.class || type == Byte.class;
    }

    @Override
    protected byte[] innerToBytes(Class<?> type, Object value) {
        return new byte[]{((Byte)value).byteValue()};
    }

    @Override
    protected Object innertoObject(Class<?> type, byte[] bytes) {
        Util.checkLength(bytes, 1);
        return bytes[0];
    }
}
