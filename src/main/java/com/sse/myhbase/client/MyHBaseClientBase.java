package com.sse.myhbase.client;

import com.sse.myhbase.config.HBaseDataSource;
import com.sse.myhbase.config.HBaseTableConfig;

/**
 * @Author: Cai Shunda
 * @Description: MyHBaseClient接口的骨架skeleton实现
 * @Date: Created in 21:51 2017/11/1
 * @Modified by:
 */
abstract public class MyHBaseClientBase implements MyHBaseClient{
    /**
     * hbases数据源（物理数据源）
     * */
    protected HBaseDataSource hbaseDataSource;
    /**
     * hbase表配置（逻辑表结构）
     * */
    protected HBaseTableConfig hbaseTableConfig;
}
