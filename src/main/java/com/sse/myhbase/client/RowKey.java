package com.sse.myhbase.client;

/**
 * @Author: Cai Shunda
 * @Description: hbase的row key
 * @Date: Created in 22:45 2017/11/7
 * @Modified by:
 */
public interface RowKey {
    /**
     * @Author: Cai Shunda
     * @Description: 把rowkey转成bytes
     * @Date: 21:10 2017/11/8
     */
    public byte[] toBytes();
}
