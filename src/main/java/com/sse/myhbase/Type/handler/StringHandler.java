package com.sse.myhbase.Type.handler;

import com.sse.myhbase.Type.AbstractTypeHandler;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * @Author: Cai Shunda
 * @Description:
 * @Date: Created in 22:26 2017/11/9
 * @Modified by:
 */
public class StringHandler extends AbstractTypeHandler{
    @Override
    protected boolean aboutToHandler(Class<?> type) {
        return type == String.class;
    }

    @Override
    protected byte[] innerToBytes(Class<?> type, Object value) {
        return Bytes.toBytes((String) value);
    }

    @Override
    protected Object innertoObject(Class<?> type, byte[] bytes) {
        return Bytes.toString(bytes);
    }
}
