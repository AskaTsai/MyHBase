package com.sse.myhbase.client;

import com.sse.myhbase.exception.MyHBaseException;
import com.sse.myhbase.util.Util;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.Date;

/**
 * @Author: Cai Shunda
 * @Description: 执行HBase查询中需要的一些额外信息
 *              目前有，最大版本，时间标签的范围（最小和最大），查询的Rowkey范围
 * @Date: Created in 21:49 2017/11/23
 * @Modified by:
 */
public class QueryExtInfo {
    private boolean isMaxVersionSet;
    private int maxVerions;

    private boolean isTimeRangeSet;
    private long minStamp;
    private long maxStamp;

    private boolean isLimitSet;
    private long startIndex;
    private long length;

    public void setMaxVerions(int maxVerions) {
        if (maxVerions < 1) {
            throw new MyHBaseException("maxVersions is smaller than 1. maxVersions=" + maxVerions);
        }
        this.maxVerions = maxVerions;
        this.isMaxVersionSet = true;
    }

    public boolean isMaxVersionSet() {
        return isMaxVersionSet;
    }

    public int getMaxVerions() {
        return maxVerions;
    }

    public void setTimeStamp(Date timeStamp) {
        Util.checkNull(timeStamp);
        setTimeStamp(timeStamp.getTime());
    }

    public void setTimeStamp(long timeStamp) {
        setTimeRange(timeStamp, timeStamp + 1);
    }

    public void setTimeRange(long minStamp, long maxStamp) {
        if (maxStamp < minStamp) {
            throw new MyHBaseException("maxStamp is smaller than minStamp. minStamp=" + minStamp + " maxStamp=" + maxStamp);
        }
        this.minStamp = minStamp;
        this.maxStamp = maxStamp;
        this.isTimeRangeSet = true;
    }

    public void setTimeRange(Date minStamp, Date maxStamp) {
        Util.checkNull(minStamp);
        Util.checkNull(maxStamp);
        setTimeRange(minStamp.getTime(), maxStamp.getTime());
    }

    public boolean isTimeRangeSet() {
        return isTimeRangeSet;
    }

    public long getMinStamp() {
        return minStamp;
    }

    public long getMaxStamp() {
        return maxStamp;
    }

    public void setLimit(long startIndex, long length) {
        if (startIndex < 0) {
            throw new MyHBaseException("startIndex is invalid. startIndex=" + startIndex);
        }
        if (length < 1) {
            throw new MyHBaseException("length is invalid. length=" + length);
        }
        this.startIndex = startIndex;
        this.length = length;
        this.isLimitSet = true;
    }

    public boolean isLimitSet() {
        return isLimitSet;
    }

    public long getStartIndex() {
        return startIndex;
    }

    public long getLength() {
        return length;
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
