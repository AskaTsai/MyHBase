package com.sse.myhbase.client.rowkey;

import com.sse.myhbase.client.RowKey;
import com.sse.myhbase.util.EncodingUtil;
import com.sse.myhbase.util.Util;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * @author: Cai Shunda
 * @description: 字节数组类型的rowkwy
 * @date: Created in 11:20 2017/11/8
 * @modified by:
 */
public class BytesRowKey implements RowKey{
    private byte[] key;

    public BytesRowKey(byte[] key) {
        Util.checkNull(key);
        this.key = key;
    }

    @Override
    public byte[] toBytes() {
        return new byte[0];
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return "BytesRowKey [key=" + EncodingUtil.toHexString(key) + "]";
    }
}
