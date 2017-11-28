package com.sse.myhbase.client.rowkey.handler;

import com.sse.myhbase.client.RowKey;

/**
 * @author: Cai Shunda
 * @description:  把hbase的行键转化为行键对象
 * @date: Created in 22:20 2017/11/7
 * @modified by:
 */
public interface RowKeyHandler {
    /**
     * @author: Cai Shunda
     * @description: 把hbase的行键由bytes转化为行键java对象
     * @date: 11:18 2017/11/8
     */
    public RowKey convert(byte[] row);
}
