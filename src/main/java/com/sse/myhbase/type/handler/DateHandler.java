package com.sse.myhbase.type.handler;

import com.sse.myhbase.type.AbstractTypeHandler;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.Date;

/**
 * @Author: Cai Shunda
 * @Description:
 * @Date: Created in 22:59 2017/11/9
 * @Modified by:
 */
public class DateHandler extends AbstractTypeHandler{
    @Override
    protected boolean aboutToHandler(Class<?> type) {
        return type == Date.class;
    }

    @Override
    protected byte[] innerToBytes(Class<?> type, Object value) {
        long time = ((Date)value).getTime();
        return Bytes.toBytes(time);
    }

    @Override
    protected Object innertoObject(Class<?> type, byte[] bytes) {
        long time = Bytes.toLong(bytes);
        return new Date(time);
    }
}
