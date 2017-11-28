package com.sse.myhbase.client;

/**
 * @author: Cai Shunda
 * @description: hbase的row key
 * @date: Created in 22:45 2017/11/7
 * @modified by:
 */
public interface RowKey {
    /**
     * @author: Cai Shunda
     * @description: 把rowkey转成bytes
     * @date: 21:10 2017/11/8
     */
    public byte[] toBytes();
}
