package com.sse.myhbase.client.rowkey.handler;

import com.sse.myhbase.client.RowKey;
import com.sse.myhbase.client.rowkey.BytesRowKey;

/**
 * @Author: Cai Shunda
 * @Description:
 * @Date: Created in 11:16 2017/11/8
 * @Modified by:
 */
public class BytesRowKeyHandler implements RowKeyHandler{
    @Override
    public RowKey convert(byte[] row) {
        return new BytesRowKey(row);
    }
}
