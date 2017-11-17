package com.sse.myhbase.client;

import com.sse.myhbase.config.HBaseDataSource;
import com.sse.myhbase.config.HBaseTableConfig;
import com.sse.myhbase.config.HBaseTableSchema;
import com.sse.myhbase.util.StringUtil;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;

import java.util.ArrayList;

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
    protected HBaseDataSource hBaseDataSource;
    /**
     * hbase表配置（逻辑表结构）
     * */
    protected HBaseTableConfig hBaseTableConfig;

    @Override
    public HBaseAdmin getHBaseAdmin() {
        return this.hBaseDataSource.getHBaseAdmin();
    }

    @Override
    public HTable getTable(String tableName) {
        return this.hBaseDataSource.getHTable(tableName);
    }

    @Override
    public HBaseDataSource getHBaseDataSource() {
        return this.hBaseDataSource;
    }
    @Override
    public void setHBaseDataSource(HBaseDataSource hBaseDataSource) {
        this.hBaseDataSource = hBaseDataSource;
    }

    @Override
    public HBaseTableConfig getHBaseTableConfig() {
        return this.hBaseTableConfig;
    }

    @Override
    public void setHBaseTableConfig(HBaseTableConfig hBaseTableConfig) {
        this.hBaseTableConfig = hBaseTableConfig;
    }

    @Override
    public boolean isAutoCreate() {
        return hBaseTableConfig.isAutoCreate();
    }


}
