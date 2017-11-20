package com.sse.myhbase.client;

import com.sse.myhbase.config.HBaseColumnSchema;
import com.sse.myhbase.config.HBaseDataSource;
import com.sse.myhbase.config.HBaseTableConfig;
import com.sse.myhbase.config.HBaseTableSchema;
import com.sse.myhbase.exception.MyHBaseException;
import com.sse.myhbase.type.TypeHandler;
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

    /**
     * @Author: Cai Shunda
     * @Description: 获取表连接
     * @Param:
     * @Date: 22:07 2017/11/20
     */
    protected HTable getHTable() {
        return hBaseDataSource.getHTable(hBaseTableConfig.gethBaseTableSchema().getTableName());
    }

    /**
     * @Author: Cai Shunda
     * @Description: 获取配置里面的TypeInfo
     * @Param:
     * @Date: 21:30 2017/11/19
     */
    protected TypeInfo findTypeInfo(Class<?> type) {
        return hBaseTableConfig.findTypeInfo(type);
    }

    /**
     * @Author: Cai Shunda
     * @Description: 把java实例t对应columnInfo的属性转化成bytes
     * @Param:
     * @Date: 21:04 2017/11/20
     */
    protected <T> byte[] convertPOJOFieldToBytes(T t, ColumnInfo columnInfo) {
        try {
            Object value = columnInfo.field.get(t);
            return convertValueToBytes(value, columnInfo);
        } catch (Exception e) {
            throw new MyHBaseException(e);
        }
    }

    /**
     * @Author: Cai Shunda
     * @Description: 把value安装对应类型转化成bytes
     * @Param:
     * @Date: 21:07 2017/11/20
     */
    protected byte[] convertValueToBytes(Object value, ColumnInfo columnInfo) {
        HBaseColumnSchema hBaseColumnSchema = columnSchema(columnInfo.family, columnInfo.qualifier);
        return convertValueToBytes(value, hBaseColumnSchema);
    }

    /**
     * @Author: Cai Shunda
     * @Description: 把value安装对应类型转化成bytes
     * @Param:
     * @Date: 21:10 2017/11/20
     */
    protected byte[] convertValueToBytes(Object value, HBaseColumnSchema hBaseColumnSchema) {
        TypeHandler typeHandler = hBaseColumnSchema.getTypeHandler();
        return typeHandler.toBytes(hBaseColumnSchema.getType(), value);
    }

    protected HBaseColumnSchema columnSchema(String family, String qualifier) {
        return hBaseTableConfig.gethBaseTableSchema().findColumnSchema(family, qualifier);
    }
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
