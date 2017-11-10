package com.sse.myhbase.type.handler;

import com.sse.myhbase.type.AbstractTypeHandler;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * @Author: Cai Shunda
 * @Description:
 * @Date: Created in 20:06 2017/11/10
 * @Modified by:
 */
public class BooleanHandler extends AbstractTypeHandler {
    @Override
    protected boolean aboutToHandler(Class<?> type) {
        return type == Boolean.class || type == boolean.class;
    }

    @Override
    protected byte[] innerToBytes(Class<?> type, Object value) {
        return Bytes.toBytes((Boolean) value);
    }

    @Override
    protected Object innertoObject(Class<?> type, byte[] bytes) {
        return Bytes.toBoolean(bytes);
    }
}
