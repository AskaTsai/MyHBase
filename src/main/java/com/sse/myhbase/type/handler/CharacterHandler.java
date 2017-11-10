package com.sse.myhbase.type.handler;

import com.sse.myhbase.type.AbstractTypeHandler;

/**
 * @Author: Cai Shunda
 * @Description:
 * @Date: Created in 20:14 2017/11/10
 * @Modified by:
 */
public class CharacterHandler extends AbstractTypeHandler{
    @Override
    protected boolean aboutToHandler(Class<?> type) {
        return type == Character.class || type == char.class;
    }

    @Override
    protected byte[] innerToBytes(Class<?> type, Object value) {
        char c = ((Character)value).charValue();
        byte[] result = new byte[2];
        result[1] = (byte)c;
        result[0] = (byte)(c>>>8);
        return result;
    }

    @Override
    protected Object innertoObject(Class<?> type, byte[] bytes) {
        int i = 0;
        i = i ^ bytes[0];
        i = i<<8;
        i = i ^ bytes[1];
        return (char) i;
    }
}
