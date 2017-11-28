package com.sse.myhbase.hbase;

import org.apache.hadoop.hbase.client.HTable;

/**
 * @author: Cai Shunda
 * @description: 用户自定义表连接池服务的接口
 * @date: Created in 18:44 2017/11/4
 * @modified by:
 */
public interface HTablePoolService {

    /**
     * @author: Cai Shunda
     * @description: 从连接池中获取特点的表连接
     * @date: 14:24 2017/11/7
     */
    public HTable getTable(String tableName);

    /**
     * @author: Cai Shunda
     * @description: 从连接池中获取特点的表连接
     * @date: 14:24 2017/11/7
     */
    public HTable getTable(byte[] tableName);


}
