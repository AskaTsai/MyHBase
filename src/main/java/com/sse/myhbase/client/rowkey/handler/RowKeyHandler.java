package com.sse.myhbase.client.rowkey.handler;

import com.sse.myhbase.client.RowKey;

/**
 * @Author: Cai Shunda
 * @Description:  把hbase的行键转化为行键对象
 * @Date: Created in 22:20 2017/11/7
 * @Modified by:
 */
public interface RowKeyHandler {
    /**
     * @Author: Cai Shunda
     * @Description: 把hbase的行键由bytes转化为行键java对象
     * @Date: 11:18 2017/11/8
     */
    public RowKey convert(byte[] row);
}
