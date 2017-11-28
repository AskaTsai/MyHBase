package com.sse.myhbase.client;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author: Cai Shunda
 * @description: 将查询获取的cell转化成DO
 * @date: Created in 21:15 2017/11/23
 * @modified by:
 */
public class MyHBaseDOWithKeyResult<T> {

    /**
     * 行键
     */
    private RowKey rowKey;

    /**
     * 最终的结果
     */
    private T t;

    public RowKey getRowKey() {
        return rowKey;
    }

    public void setRowKey(RowKey rowKey) {
        this.rowKey = rowKey;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
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
