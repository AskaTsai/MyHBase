package com.sse.myhbase.client;

import com.sse.myhbase.core.Nullable;
import com.sse.myhbase.util.Util;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.Date;

/**
 * @Author: Cai Shunda
 * @Description:
 * @Date: Created in 17:06 2017/11/19
 * @Modified by:
 */
public class PutRequest<T> {
    private RowKey rowKey;
    private T t;
    @Nullable
    private Long timestamp;

    public PutRequest(RowKey rowKey, T t) {
        this.rowKey = rowKey;
        this.t = t;
    }

    public PutRequest(RowKey rowKey, T t, Long timestamp) {
        this.rowKey = rowKey;
        this.t = t;
        this.timestamp = timestamp;
    }

    public PutRequest(RowKey rowKey, T t, Date timestamp) {
        Util.checkNull(timestamp);
        this.rowKey = rowKey;
        this.t = t;
        this.timestamp = timestamp.getTime();
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public void setTimestamp(Date timestamp) {
        Util.checkNull(timestamp);
        this.timestamp = timestamp.getTime();
    }

    public void cleanTimestamp() {
        this.timestamp = null;
    }

    public RowKey getRowKey() {
        return rowKey;
    }

    public T getT() {
        return t;
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
        return ToStringBuilder.reflectionToString(this);
    }
}
