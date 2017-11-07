package com.sse.myhbase.hbase;

import org.apache.hadoop.hbase.client.HTable;

/**
 * @Author: Cai Shunda
 * @Description: 用户自定义表连接池服务的接口
 * @Date: Created in 18:44 2017/11/4
 * @Modified by:
 */
public interface HTablePoolService {

    /**
     * @Author: Cai Shunda
     * @Description: 从连接池中获取特点的表连接
     * @Date: 14:24 2017/11/7
     */
    public HTable getTable(String tableName);

    /**
     * @Author: Cai Shunda
     * @Description: 从连接池中获取特点的表连接
     * @Date: 14:24 2017/11/7
     */
    public HTable getTable(byte[] tableName);


}
