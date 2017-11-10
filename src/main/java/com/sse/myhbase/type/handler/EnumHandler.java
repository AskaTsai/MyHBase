package com.sse.myhbase.type.handler;

import com.sse.myhbase.type.AbstractTypeHandler;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * @Author: Cai Shunda
 * @Description:
 * @Date: Created in 21:44 2017/11/10
 * @Modified by:
 */
public class EnumHandler extends AbstractTypeHandler{
    @Override
    protected boolean aboutToHandler(Class<?> type) {
        return type.isEnum();
    }

    @Override
    protected byte[] innerToBytes(Class<?> type, Object value) {
        String name = ((Enum<?>)value).name();
        return Bytes.toBytes(name);
    }

    @Override
    protected Object innertoObject(Class type, byte[] bytes) {
        String name = Bytes.toString(bytes);
        return Enum.valueOf(type,name);
    }
}
