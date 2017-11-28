package com.sse.myhbase.client;

import com.sse.myhbase.client.rowkey.handler.RowKeyHandler;
import com.sse.myhbase.config.HBaseColumnSchema;
import com.sse.myhbase.config.HBaseDataSource;
import com.sse.myhbase.config.HBaseTableConfig;
import com.sse.myhbase.config.MyHBaseRuntimeSetting;
import com.sse.myhbase.core.Nullable;
import com.sse.myhbase.exception.MyHBaseException;
import com.sse.myhbase.type.TypeHandler;
import com.sse.myhbase.util.Util;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.Filter;

import java.util.List;

/**
 * @author: Cai Shunda
 * @description: MyHBaseClient接口的骨架skeleton实现
 * @date: Created in 21:51 2017/11/1
 * @modified by:
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
     * hbase的运行时配置
     */
    protected MyHBaseRuntimeSetting myHBaseRuntimeSetting = new MyHBaseRuntimeSetting();

    /**
     * @author: Cai Shunda
     * @description: 获取表连接
     * @Param:
     * @date: 22:07 2017/11/20
     */
    protected HTable getHTable() {
        return hBaseDataSource.getHTable(hBaseTableConfig.gethBaseTableSchema().getTableName());
    }

    /**
     * @author: Cai Shunda
     * @description: 获取配置里面的TypeInfo
     * @Param:
     * @date: 21:30 2017/11/19
     */
    protected TypeInfo findTypeInfo(Class<?> type) {
        return hBaseTableConfig.findTypeInfo(type);
    }

    /**
     * @author: Cai Shunda
     * @description: 把java实例t对应columnInfo的属性转化成bytes
     * @Param:
     * @date: 21:04 2017/11/20
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
     * @author: Cai Shunda
     * @description: 把value安装对应类型转化成bytes
     * @Param:
     * @date: 21:07 2017/11/20
     */
    protected byte[] convertValueToBytes(Object value, ColumnInfo columnInfo) {
        HBaseColumnSchema hBaseColumnSchema = findColumnSchema(columnInfo.family, columnInfo.qualifier);
        return convertValueToBytes(value, hBaseColumnSchema);
    }

    /**
     * @author: Cai Shunda
     * @description: 把value安装对应类型转化成bytes
     * @Param:
     * @date: 21:10 2017/11/20
     */
    protected byte[] convertValueToBytes(Object value, HBaseColumnSchema hBaseColumnSchema) {
        TypeHandler typeHandler = hBaseColumnSchema.getTypeHandler();
        return typeHandler.toBytes(hBaseColumnSchema.getType(), value);
    }

    /**
     * @author: Cai Shunda
     * @description: 获取配置中对应Family和qualifier的HBase列配置
     * @Param:
     * @date: 20:25 2017/11/24
     */
    protected HBaseColumnSchema findColumnSchema(String family, String qualifier) {
        return hBaseTableConfig.gethBaseTableSchema().findColumnSchema(family, qualifier);
    }

    /**
     * @author: Cai Shunda
     * @description: 构建HBase查询请求的Get
     * @Param:
     * @date: 20:25 2017/11/24
     */
    protected Get constructGet(RowKey rowKey, @Nullable Filter filter) {
        Util.checkRowKey(rowKey);

        Get get = new Get(rowKey.toBytes());
        get.setFilter(filter);
        return get;
    }

    /**
     * @author: Cai Shunda
     * @description: 为get根据type指定family和qualifier
     * @Param:
     * @date: 20:39 2017/11/24
     */
    protected <T> void applyRequestFamilyAndQualifier(Class<? extends T> type, Get get) {
        TypeInfo typeInfo = findTypeInfo(type);
        List<ColumnInfo> columnInfoList = typeInfo.getColumnInfos();
        for (ColumnInfo columnInfo : columnInfoList) {
            get.addColumn(columnInfo.familyBytes, columnInfo.qualifierBytes);
        }
    }

    /**
     * @author: Cai Shunda
     * @description: 构建HBase查询请求的Scan
     * @Param:
     * @date: 21:22 2017/11/27
     */
    protected Scan constructScan(RowKey startKey, RowKey endKey, @Nullable Filter filter, @Nullable QueryExtInfo queryExtInfo) {
        Util.checkRowKey(startKey);
        Util.checkRowKey(endKey);

        Scan scan = new Scan();
        scan.setStartRow(startKey.toBytes());
        scan.setStopRow(endKey.toBytes());


        int cachingSize = myHBaseRuntimeSetting.getScanCachingSize();
        if (myHBaseRuntimeSetting.isIntelligentSacnSize()) {
            //待完善，这样最大每次只能扫描到MAX_VALUE,讲道理int的最大值2的31次方，21亿足够了
            if (queryExtInfo != null && queryExtInfo.isLimitSet()) {
                long limitScanSize = queryExtInfo.getStartIndex() + queryExtInfo.getLength();
                if (limitScanSize > Integer.MAX_VALUE) {
                    cachingSize = Integer.MAX_VALUE;
                } else {
                    cachingSize = (int) limitScanSize;
                }
            }
        }

        scan.setFilter(filter);
        return scan;
    }

    /**
     * @author: Cai Shunda
     * @description: 为scan根据type指定family和qualifier
     * @Param:
     * @date: 21:41 2017/11/27
     */
    protected <T> void applyRequestFamilyAndQualifier(Class<? extends T> type, Scan scan) {
        TypeInfo typeInfo = findTypeInfo(type);
        List<ColumnInfo> columnInfoList = typeInfo.getColumnInfos();
        for (ColumnInfo columnInfo : columnInfoList) {
            scan.addColumn(columnInfo.familyBytes, columnInfo.qualifierBytes);
        }
    }

    /**
     * @author: Cai Shunda
     * @description: 把HBase查询结果依据需要的POJO类型type转化为对应类型
     * @Param:
     * @date: 20:45 2017/11/24
     */
    protected <T> MyHBaseDOWithKeyResult<T> convertToMyHBaseDOWithKeyResult(Result hbaseResult, Class<? extends T> type) {
        Cell[] cells = hbaseResult.rawCells();
        if (cells == null || cells.length == 0) {
            return null;
        }
        String familyStr = null;
        String qualifierStr = null;
        RowKeyHandler rowKeyHandler = null;

        try {
            TypeInfo typeInfo = findTypeInfo(type);
            T result = type.newInstance();

            //处理每一个cell
            for (Cell cell : cells) {
                familyStr = new String(CellUtil.cloneFamily(cell));
                qualifierStr = new String(CellUtil.cloneQualifier(cell));
                byte[] hbaseValue = CellUtil.cloneValue(cell);

                ColumnInfo columnInfo = typeInfo.findColumnInfo(familyStr, qualifierStr);

                HBaseColumnSchema hBaseColumnSchema = findColumnSchema(columnInfo.family, columnInfo.qualifier);

                TypeHandler typeHandler = hBaseColumnSchema.getTypeHandler();

                //这里type是列值的type，参数里的type是整个POJO的类型
                Object value = typeHandler.toObject(hBaseColumnSchema.getType(), hbaseValue);

                if (value != null) {
                    columnInfo.field.set(result, value);
                }
            }
            //重置为空
            familyStr = "";
            qualifierStr = "";

            byte[] row = CellUtil.cloneRow(cells[0]);
            rowKeyHandler = hBaseTableConfig.gethBaseTableSchema().getRowKeyHandler();
            RowKey rowKey = rowKeyHandler.convert(row);

            MyHBaseDOWithKeyResult<T> pojoWithKey = new MyHBaseDOWithKeyResult<>();
            pojoWithKey.setRowKey(rowKey);
            pojoWithKey.setT(result);

            return pojoWithKey;
        } catch (Exception e) {
            throw new MyHBaseException("convert result exception. familyStr=" + familyStr
                    + " qualifierStr=" + qualifierStr
                    + " rowKeyHandler=" + rowKeyHandler + " result="
                    + hbaseResult + " type=" + type, e);
        }
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
