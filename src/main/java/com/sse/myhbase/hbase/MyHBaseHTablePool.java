package com.sse.myhbase.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.HTable;

/**
 * @Author: Cai Shunda
 * @Description:
 * @Date: Created in 15:17 2017/11/7
 * @Modified by:
 */
public class MyHBaseHTablePool implements HTablePoolService{

    public MyHBaseHTablePool(Connection connection, long flushInterval) {
    }

    @Override
    public HTable getTable(String tableName) {
        return null;
    }

    @Override
    public HTable getTable(byte[] tableName) {
        return null;
    }
}
