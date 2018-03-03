package com.sse.myhbase.type.ext;

import com.sse.myhbase.util.EncodingUtil;
import com.sse.myhbase.util.Util;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * @author: Cai Shunda
 * @description: 16进制数组
 * @date: Created in 20:42 2018/3/1
 * @modified by:
 */
public class HexBytes {
    private byte[] date;

    public HexBytes(byte[] date) {
        Util.checkNull(date);
        this.date = date;
    }

    public HexBytes(String hexStr) {
        Util.checkNull(hexStr);
        this.date = EncodingUtil.parseBytesFromHexString(hexStr);
    }

    public byte[] getDate() {
        return date;
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
        return "HexBytes=[" + EncodingUtil.toHexString(date) + "]";
    }
}
