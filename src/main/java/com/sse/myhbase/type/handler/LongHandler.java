package com.sse.myhbase.type.handler;

import com.sse.myhbase.type.AbstractTypeHandler;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * @Author: Cai Shunda
 * @Description:
 * @Date: Created in 21:21 2017/11/10
 * @Modified by:
 */
public class LongHandler extends AbstractTypeHandler{
    @Override
    protected boolean aboutToHandler(Class<?> type) {
        return type == long.class || type == Long.class;
    }

    @Override
    protected byte[] innerToBytes(Class<?> type, Object value) {
        return Bytes.toBytes((Long)value);
    }

    @Override
    protected Object innertoObject(Class<?> type, byte[] bytes) {
        return Bytes.toLong(bytes);
    }
}
