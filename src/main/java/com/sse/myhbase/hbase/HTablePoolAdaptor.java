package com.sse.myhbase.hbase;

import com.sse.myhbase.exception.MyHBaseException;
import com.sse.myhbase.util.Util;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.HTable;

import java.io.IOException;



/**
 * @Author: Cai Shunda
 * @Description: hbase表连接池的默认实现
 * @Date: Created in 15:16 2017/11/7
 * @Modified by:
 */
public class HTablePoolAdaptor implements HTablePoolService {

    private Connection connection;

    public HTablePoolAdaptor(Connection connection) throws IOException {
        Util.checkNull(connection);
        this.connection = connection;
    }

    @Override
    public HTable getTable(String tableName) {
        Util.checkNull(connection);
        try {
            return (HTable) connection.getTable(TableName.valueOf(tableName));
        } catch (IOException e) {
            throw new MyHBaseException("can not get htable with tablename=" + tableName);
        }
    }

    @Override
    public HTable getTable(byte[] tableName) {
        Util.checkNull(connection);
        try {
            return (HTable) connection.getTable(TableName.valueOf(tableName));
        } catch (IOException e) {
            throw new MyHBaseException("can not get htable with tablename=" + new String(tableName));
        }
    }
}
