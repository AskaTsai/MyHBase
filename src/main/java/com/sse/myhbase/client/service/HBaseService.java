package com.sse.myhbase.client.service;

import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;

/**
 * @author: Cai Shunda
 * @description: HBaseService包含获取表连接实例和HBaseAdmin
 * @date: Created in 15:10 2017/11/13
 * @modified by:
 */
public interface HBaseService {
    /**
     * @author: Cai Shunda
     * @description: 获取指定表名的HTable连接实例
     * @date: 15:11 2017/11/13
     */
    public HTable getTable(String tableName);

    /**
     * @author: Cai Shunda
     * @description: 获取HBaseAdmin
     * @date: 15:12 2017/11/13
     */
    public HBaseAdmin getHBaseAdmin();
}
