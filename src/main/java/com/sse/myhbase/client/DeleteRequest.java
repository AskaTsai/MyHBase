package com.sse.myhbase.client;

import com.sse.myhbase.core.Nullable;
import com.sse.myhbase.util.Util;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.Date;

/**
 * @author: Cai Shunda
 * @description:
 * @date: Created in 21:35 2017/11/29
 * @modified by:
 */
public class DeleteRequest {
    private RowKey rowKey;

    @Nullable
    private Long timestamp;


    public DeleteRequest(RowKey rowKey) {
        this.rowKey = rowKey;
    }

    public DeleteRequest(RowKey rowKey, long timestamp) {
        this.rowKey = rowKey;
        this.timestamp = timestamp;
    }

    public DeleteRequest(RowKey rowKey, Date timestamp) {
        Util.checkNull(timestamp);
        this.rowKey = rowKey;
        this.timestamp = timestamp.getTime();
    }

    public RowKey getRowKey() {
        return rowKey;
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
