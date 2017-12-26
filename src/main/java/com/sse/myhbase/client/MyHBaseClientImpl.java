package com.sse.myhbase.client;

import com.sse.myhbase.client.rowkey.BytesRowKey;
import com.sse.myhbase.config.HBaseColumnSchema;
import com.sse.myhbase.config.HBaseTableConfig;
import com.sse.myhbase.config.HBaseTableSchema;
import com.sse.myhbase.core.Nullable;
import com.sse.myhbase.exception.MyHBaseException;
import com.sse.myhbase.util.Util;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


/**
 * @author: Cai Shunda
 * @description: MyHBaseClinet的默认实现
 * @date: Created in 21:51 2017/11/1
 * @modified by:
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
     * @author: Cai Shunda
     * @description: 自动创建HBaseTableConfig设置的表
     * @date: 20:52 2017/11/17
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

    @Override
    public <T> T findObject(RowKey rowKey, Class<? extends T> type) {
        return unwrap(findObjectAndKey(rowKey, type));
    }

    private <T> T unwrap(MyHBaseDOWithKeyResult<T> myHBaseDOWithKeyResult) {
        if (myHBaseDOWithKeyResult == null) {
            return null;
        }
        return myHBaseDOWithKeyResult.getT();
    }

    @Override
    public <T> MyHBaseDOWithKeyResult<T> findObjectAndKey(RowKey rowKey, Class<? extends T> type) {
        return findObjectAndKey(rowKey, type, null);
    }

    @Override
    public <T> MyHBaseDOWithKeyResult<T> findObjectAndKey(RowKey rowKey, Class<? extends T> type, QueryExtInfo queryExtInfo) {
        return findObjectAndKey_internal(rowKey, type, null, queryExtInfo);
    }

    private <T> MyHBaseDOWithKeyResult<T> findObjectAndKey_internal(RowKey rowKey, Class<? extends T> type, @Nullable Filter filter , @Nullable QueryExtInfo queryExtInfo) {
        Util.checkRowKey(rowKey);
        Util.checkNull(type);

        //获取连接
        HTable hTable = getHTable();
        try {
            Get get = constructGet(rowKey, filter);

            if (queryExtInfo != null) {
                //只查询1个版本
                queryExtInfo.setMaxVerions(1);
                if (queryExtInfo.isMaxVersionSet()) {
                    get.setMaxVersions(queryExtInfo.getMaxVerions());
                }
                if (queryExtInfo.isTimeRangeSet()) {
                    get.setTimeRange(queryExtInfo.getMinStamp(), queryExtInfo.getMaxStamp());
                }
            }

            applyRequestFamilyAndQualifier(type, get);

            return convertToMyHBaseDOWithKeyResult(hTable.get(get), type);
        } catch (Exception e) {
            throw new MyHBaseException("findObjectAndKey_internal. rowKey=" + rowKey + " type=" + type, e);
        } finally {
            Util.close(hTable);
        }
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

    @Override
    public <T> List<T> findObjectList(RowKey startRowKey, RowKey endRowKey, Class<? extends T> type) {
        return unwrap(findObjectAndKeyList(startRowKey, endRowKey, type));
    }

    private <T> List<T> unwrap(List<MyHBaseDOWithKeyResult<T>> myHBaseDOWithKeyResultList) {
        List<T> resultList = new ArrayList<>();
        if (!myHBaseDOWithKeyResultList.isEmpty()) {
            for (MyHBaseDOWithKeyResult<T> t : myHBaseDOWithKeyResultList) {
                resultList.add(unwrap(t));
            }
        }
        return resultList;
    }

    @Override
    public <T> List<MyHBaseDOWithKeyResult<T>> findObjectAndKeyList(RowKey startRowKey, RowKey endRowKey, Class<? extends T> type) {
        return findObjectAndKeyList(startRowKey, endRowKey, type, null);
    }

    @Override
    public <T> List<MyHBaseDOWithKeyResult<T>> findObjectAndKeyList(RowKey startRowKey, RowKey endRowKey, Class<? extends T> type, QueryExtInfo queryExtInfo) {
        return findObjectAndKeyList_internal(startRowKey, endRowKey, type, null, queryExtInfo);
    }

    @Override
    public void deleteObject(RowKey rowKey, Class<?> type) {
        List<RowKey> rowKeyList = new ArrayList<>();
        rowKeyList.add(rowKey);
        deleteObjectList(rowKeyList, type);
    }

    @Override
    public void deleteObjectList(List<RowKey> rowKeyList, Class<?> type) {
        Util.checkNull(rowKeyList);
        List<DeleteRequest> deleteRequestList = new ArrayList<>();

        for (RowKey rowKey: rowKeyList) {
            deleteRequestList.add(new DeleteRequest(rowKey));
        }

        deleteObjectListByRowKey_internal(deleteRequestList, type);
    }

    @Override
    public void deleteObjectWithRange(RowKey startRowKey, RowKey endRowKey, Class<?> type) {
        Util.checkRowKey(startRowKey);
        Util.checkRowKey(endRowKey);
        Util.checkNull(type);

        TypeInfo typeInfo = findTypeInfo(type);
        List<ColumnInfo> columnInfoList = typeInfo.getColumnInfos();
        deleteInternalWithScanFirst(startRowKey, endRowKey, null, columnInfoList, null, null);
    }

    private void deleteObjectListByRowKey_internal(List<DeleteRequest> deleteRequestList, Class<?> type) {
        Util.checkNull(deleteRequestList);
        Util.checkNull(type);

        if (deleteRequestList.isEmpty()) {
            return;
        }

        for (DeleteRequest deleteRequest : deleteRequestList) {
            Util.checkDeleteRequest(deleteRequest);
        }

        TypeInfo typeInfo = findTypeInfo(type);
        List<ColumnInfo> columnInfoList = typeInfo.getColumnInfos();

        //待理解,why linkedList
        List<Delete> deletes = new LinkedList<>();

        for (DeleteRequest deleteRequest : deleteRequestList) {
            Delete delete = new Delete(deleteRequest.getRowKey().toBytes());
            for (ColumnInfo columnInfo : columnInfoList) {
                if (deleteRequest.getTimestamp() == null) {
                    //删除所有版本
                    delete.addColumn(columnInfo.familyBytes, columnInfo.qualifierBytes);
                } else {
                    //删除指定版本
                    delete.addColumn(columnInfo.familyBytes, columnInfo.qualifierBytes, deleteRequest.getTimestamp());
                }
            }
            deletes.add(delete);
        }

        HTable hTable = getHTable();
        try {
            hTable.delete(deletes);
        } catch (IOException e) {
            throw new MyHBaseException("deleteObjectList_internal. deleteRequestList = "
                    + deleteRequestList, e);
        } finally {
            Util.close(hTable);
        }

        //一旦所有删除请求都执行了， 则删除请求列表都会被清空
        if (deletes.size() > 0) {
            throw new MyHBaseException("deleteObjectList_internal. deletes = "
                    + deletes);
        }
    }

    /**
     * @Author: Cai Shunda
     * @Description:
     * @Param:
     * @Date: 16:39 2017/12/25
     */
    private void deleteInternalWithScanFirst(
            RowKey startRowKey, RowKey endRowKey,
            @Nullable Filter filter, @Nullable List<ColumnInfo> columnInfoList,
            @Nullable List<HBaseColumnSchema> hBaseColumnSchemaList, @Nullable Date timestamp) {

        Util.check((columnInfoList != null && !columnInfoList.isEmpty())
                || (hBaseColumnSchemaList != null && !hBaseColumnSchemaList.isEmpty()));

        final int deleteBatch = getDeleteBatch();

        RowKey nextStartRowKey = startRowKey;
        while (true) {
            //scan一下要删除的目标，并构造delete
            Scan temScan = constructScan(nextStartRowKey, endRowKey, filter, null);

            List<Delete> deletes = new LinkedList<Delete>();

            HTable hTable = getHTable();
            ResultScanner resultScanner = null;

            try {
                resultScanner = hTable.getScanner(temScan);
                Result result = null;
                while ((result = resultScanner.next()) != null) {
                    Delete delete = new Delete(result.getRow());
                    //每一个result都有可能是下一次的startRowKey
                    nextStartRowKey = new BytesRowKey(result.getRow());

                    if (columnInfoList != null) {
                        for (ColumnInfo columnInfo : columnInfoList) {
                            if (timestamp == null) {
                                //删除所有版本
                                delete.addColumn(columnInfo.familyBytes, columnInfo.qualifierBytes);
                            } else {
                                //删除指定版本
                                delete.addColumn(columnInfo.familyBytes, columnInfo.qualifierBytes, timestamp.getTime());
                            }
                        }
                    }

                    if (hBaseColumnSchemaList != null) {
                        for (HBaseColumnSchema hBaseColumnSchema : hBaseColumnSchemaList) {
                            if (timestamp == null) {
                                //删除所有版本
                                delete.addColumn(hBaseColumnSchema.getFamilyBytes(), hBaseColumnSchema.getQualifierBytes());
                            } else {
                                //删除指定版本
                                delete.addColumn(hBaseColumnSchema.getFamilyBytes(), hBaseColumnSchema.getQualifierBytes(), timestamp.getTime());
                            }
                        }
                    }

                    deletes.add(delete);
                    if (deletes.size() >= deleteBatch) {
                        break;
                    }

                }
            } catch (IOException e) {
                throw new MyHBaseException("delete_internal_with_scan_first. scan = " + temScan, e);
            } finally {
                Util.close(resultScanner);
                Util.close(hTable);
            }

            final int deleteListSize = deletes.size();
            if (deleteListSize == 0) {
                return;
            }

            //实际删除操作
            try {
                hTable = getHTable();
                hTable.delete(deletes);
            } catch (IOException e) {
                throw new MyHBaseException("delete_internal_with_scan_first. scan = " + temScan, e);
            } finally {
                Util.close(hTable);
            }

            //一旦所有删除请求都执行了， 则删除请求列表都会被清空
            if (deletes.size() > 0) {
                throw new MyHBaseException("delete_internal_with_scan_first fail. deletes = "
                        + deletes);
            }

            if (deleteListSize < deleteBatch) {
                //这次循环删除的大小小于我们指定的最大批量数，可以认为在这个指定范围内已经没有需要删除的了
                return;
            }

        }

    }

    private <T> List<MyHBaseDOWithKeyResult<T>> findObjectAndKeyList_internal(
            RowKey startRowKey, RowKey endRowKey, Class<? extends T> type,
            Filter filter, QueryExtInfo queryExtInfo) {
        Util.checkNull(startRowKey);
        Util.checkNull(endRowKey);
        Util.checkNull(type);

        Scan scan = constructScan(startRowKey, endRowKey, filter, queryExtInfo);

        long startIndex = 0L;
        long length = Long.MAX_VALUE;

        if (queryExtInfo != null) {
            //只查询一个版本
            queryExtInfo.setMaxVerions(1);

            if (queryExtInfo.isMaxVersionSet()) {
                scan.setMaxVersions(queryExtInfo.getMaxVerions());
            }
            if (queryExtInfo.isTimeRangeSet()) {
                try {
                    scan.setTimeRange(queryExtInfo.getMinStamp(), queryExtInfo.getMaxStamp());
                } catch (Exception e) {
                    throw new MyHBaseException("findObjectAndKeyList_internal something wrong.", e);
                }
            }
            if (queryExtInfo.isLimitSet()) {
                startIndex = queryExtInfo.getStartIndex();
                length = queryExtInfo.getLength();
            }
        }

        applyRequestFamilyAndQualifier(type, scan);
        HTable hTable = getHTable();
        ResultScanner scanner = null;

        List<MyHBaseDOWithKeyResult<T>> resultList = new ArrayList<>();


        try {
            scanner = hTable.getScanner(scan);
            long ignoreCounter = startIndex;
            long resultCounter = 0L;
            Result result = null;
            while ((result = scanner.next()) != null) {
                //这个待优化
                if (ignoreCounter-- > 0) {
                    continue;
                }
                MyHBaseDOWithKeyResult<T> t = convertToMyHBaseDOWithKeyResult(result, type);
                if (t != null) {
                    resultList.add(t);
                    if (++ resultCounter >= length) {
                        break;
                    }
                }
            }

        } catch (Exception e) {
            throw new MyHBaseException("findObjectAndKeyList_internal. startRowKey=" + startRowKey
                    + " endRowKey=" + endRowKey + " type=" + type, e);
        } finally {
            Util.close(scanner);
            Util.close(hTable);
        }

        return resultList;

    }

    /**
     * @author: Cai Shunda
     * @description: 清理所有请求的时间标签
     * @Param:
     * @date: 21:11 2017/11/19
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
