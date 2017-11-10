package com.sse.myhbase.type.handler;

import com.sse.myhbase.type.AbstractTypeHandler;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * @Author: Cai Shunda
 * @Description:
 * @Date: Created in 21:19 2017/11/10
 * @Modified by:
 */
public class IntegerHandler extends AbstractTypeHandler{
    @Override
    protected boolean aboutToHandler(Class<?> type) {
        return type == int.class || type == Integer.class;
    }

    @Override
    protected byte[] innerToBytes(Class<?> type, Object value) {
        return Bytes.toBytes((Integer) value);
    }

    @Override
    protected Object innertoObject(Class<?> type, byte[] bytes) {
        return Bytes.toInt(bytes);
    }
}
