package com.sse.myhbase.client.service;

import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;

/**
 * @Author: Cai Shunda
 * @Description: HBaseService包含获取表连接实例和HBaseAdmin
 * @Date: Created in 15:10 2017/11/13
 * @Modified by:
 */
public interface HBaseService {
    /**
     * @Author: Cai Shunda
     * @Description: 获取指定表名的HTable连接实例
     * @Date: 15:11 2017/11/13
     */
    public HTable getTable(String tableName);

    /**
     * @Author: Cai Shunda
     * @Description: 获取HBaseAdmin
     * @Date: 15:12 2017/11/13
     */
    public HBaseAdmin getHBaseAdmin();
}
