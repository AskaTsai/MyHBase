package com.sse.myhbase.client.service;

import org.apache.hadoop.hbase.HTableDescriptor;

/**
 * @Author: Cai Shunda
 * @Description:
 * @Date: Created in 20:12 2017/11/17
 * @Modified by:
 */
public interface HBaseAdminService {
    /**
     * @Author: Cai Shunda
     * @Description: 创建表，同步操作
     * @Date: 20:13 2017/11/17
     */
    public void createTable(HTableDescriptor tableDescriptor);

    /**
     * @Author: Cai Shunda
     * @Description: 删除表，同步操作
     * @Date: 20:14 2017/11/17
     */
    public void deleteTable(final String tableName);
}
