package com.sse.myhbase.client.service;

import org.apache.hadoop.hbase.HTableDescriptor;

/**
 * @author: Cai Shunda
 * @description:
 * @date: Created in 20:12 2017/11/17
 * @modified by:
 */
public interface HBaseAdminService {
    /**
     * @author: Cai Shunda
     * @description: 创建表，同步操作
     * @date: 20:13 2017/11/17
     */
    public void createTable(HTableDescriptor tableDescriptor);

    /**
     * @author: Cai Shunda
     * @description: 删除表，同步操作
     * @date: 20:14 2017/11/17
     */
    public void deleteTable(final String tableName);
}
