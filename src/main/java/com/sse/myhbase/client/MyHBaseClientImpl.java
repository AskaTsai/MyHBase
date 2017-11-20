package com.sse.myhbase.client;

import com.sse.myhbase.config.HBaseTableConfig;
import com.sse.myhbase.config.HBaseTableSchema;
import com.sse.myhbase.exception.MyHBaseException;
import com.sse.myhbase.util.Util;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * @Author: Cai Shunda
 * @Description: MyHBaseClinet的默认实现
 * @Date: Created in 21:51 2017/11/1
 * @Modified by:
 */
public class MyHBaseClientImpl extends MyHBaseClientBase{
    private Logger logger = Logger.getLogger(MyHBaseClientImpl.class);

    @Override
    public void createTable(HTableDescriptor tableDescriptor) {
        Util.checkNull(tableDescriptor);
        HBaseAdmin admin = getHBaseAdmin();
        try {
            if (admin.tableExists(tableDescriptor.getTableName())) {
                logger.info("hbase table \"" + tableDescriptor.getTableName() + "\" exist!");
                return;
            }
            admin.createTable(tableDescriptor);
            HTableDescriptor feedback = admin.getTableDescriptor(tableDescriptor.getTableName());
            logger.info("create hbase table " + feedback);
        } catch (Exception e) {
            logger.error(e);
            throw new MyHBaseException(e);
        } finally {
            Util.close(admin);
        }
    }

    @Override
    public void deleteTable(String tableName) {
        Util.checkEmptyString(tableName);
        //1. disable 2. delete
        HBaseAdmin admin = getHBaseAdmin();
        try {
            if (admin.tableExists(tableName)) {
                if (!admin.isTableDisabled(tableName)) {
                    admin.disableTable(tableName);
                }
                admin.deleteTable(tableName);
            }
        } catch (Exception e) {
            logger.error(e);
            throw new MyHBaseException(e);
        } finally {
            Util.close(admin);
        }
    }

    /**
     * @Author: Cai Shunda
     * @Description: 自动创建HBaseTableConfig设置的表
     * @Date: 20:52 2017/11/17
     */
    @Override
    public void autoCreateTable() {
        if(isAutoCreate()){
            HBaseTableConfig tableConfig = getHBaseTableConfig();
            Util.checkNull(tableConfig);
            HBaseTableSchema schema = tableConfig.gethBaseTableSchema();
            Util.checkNull(schema);
            HTableDescriptor htd = new HTableDescriptor(TableName.valueOf(schema.getTableName()));
            List<String> familys = tableConfig.getAllConfigedFamilys();
            for (String family : familys) {
                HColumnDescriptor hcd = new HColumnDescriptor(family);
                htd.addFamily(hcd);
            }
            logger.info("trying to auto create table \'" + schema.getTableName() +"\'");
            createTable(htd);
        } else {
            logger.info("this table cannot be auto created.");
        }
    }

    @Override
    public <T> void putObject(RowKey rowKey, T t) {
        List<PutRequest<T>> putRequestList = new ArrayList<>();
        putRequestList.add(new PutRequest<T>(rowKey, t));
        putObjectList(putRequestList);
    }

    @Override
    public <T> void putObjectList(List<PutRequest<T>> putRequests) {
        cleanTimestampForPutRequest(putRequests);
        putObjectList_internal(putRequests);
    }

    private <T> void putObjectList_internal(List<PutRequest<T>> putRequests) {
        Util.checkNull(putRequests);
        if (putRequests.isEmpty()) {
            return;
        }

        for (PutRequest<T> putRequest : putRequests) {
            Util.checkPutRequest(putRequest);
        }
        //获取T对应的java对象在HBase的类型映射信息，putRequests的所有元素getT返回的都是相同类型
        TypeInfo typeInfo = findTypeInfo(putRequests.get(0).getT().getClass());

        List<Put> puts = new ArrayList<>();

        for (PutRequest<T> putRequest : putRequests) {
            Put put = new Put(putRequest.getRowKey().toBytes());
            for (ColumnInfo columnInfo: typeInfo.getColumnInfos()) {
                byte[] value =convertPOJOFieldToBytes(putRequest.getT(), columnInfo);
                if (putRequest.getTimestamp() == null) {
                    put.addColumn(columnInfo.familyBytes, columnInfo.qualifierBytes, value);
                } else {
                    put.addColumn(columnInfo.familyBytes, columnInfo.qualifierBytes, putRequest.getTimestamp().longValue(),value);
                }
            }
            puts.add(put);
        }

        //获取表连接，然后塞入数据
        HTable hTable = getHTable();
        try {
            hTable.put(puts);
        } catch (IOException e) {
            throw new MyHBaseException("putObjectList_internal putRequestList=" + putRequests);
        } finally {
            Util.close(hTable);
        }
    }


    /**
     * @Author: Cai Shunda
     * @Description: 清理所有请求的时间标签
     * @Param:
     * @Date: 21:11 2017/11/19
     */
    private <T> void cleanTimestampForPutRequest(List<PutRequest<T>> putRequests) {
        if (putRequests == null || putRequests.isEmpty()) {
            return;
        }
        for (PutRequest<T> request : putRequests) {
            if (request != null) {
                request.cleanTimestamp();
            }
        }
    }
}
