package com.sse.myhbase.config;

import com.sse.myhbase.client.rowkey.BytesRowKey;
import com.sse.myhbase.client.rowkey.handler.BytesRowKeyHandler;
import com.sse.myhbase.client.rowkey.handler.RowKeyHandler;
import com.sse.myhbase.client.rowkey.handler.RowKeyHandlerHolder;
import com.sse.myhbase.core.NotNullable;
import com.sse.myhbase.core.Nullable;
import com.sse.myhbase.exception.MyHBaseException;
import com.sse.myhbase.util.StringUtil;
import com.sse.myhbase.util.Util;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @Author: Cai Shunda
 * @Description: HBase表的Schema，包含表的所有元数据
 * @Date: Created in 20:17 2017/11/7
 * @Modified by:
 */
public class HBaseTableSchema {
    //-----------------Xml的配置--------------------
    /**
     * 表名，不能为空
     */
    @ConfigAttr
    @NotNullable
    private String tableName;

    /**
     * 默认列族，可以为空
     */
    @ConfigAttr
    @Nullable
    private String defaultFamily;

    /**
     * 行键句柄，可以为空
     */
    @ConfigAttr
    @Nullable
    private String rowKeyHandlerName;

    //-----------------运行时的配置--------------------
    /**
     * 表名bytes
     */
    private byte[] tableNameBytes;

    /**
     * 默认列族bytes
     */
    private byte[] defaultFamilyBytes;

    /**
     * 列的schema  map的规则Qualifier->Family-> HBaseColumnSchema.
     */
    private Map<String, Map<String, HBaseColoumSchema>> coloumSchemas = new TreeMap<>();

    /**
     * RowKeyHandler的实例
     */
    private RowKeyHandler rowKeyHandler;

    /**
     * 初始化
     */
    public void init(List<HBaseColoumSchema> hBaseColoumSchemas) {
        Util.checkEmptyString(tableName);
        tableNameBytes = Bytes.toBytes(tableName);

        if (StringUtil.isNotEmptyString(defaultFamily)) {
            defaultFamilyBytes = Bytes.toBytes(defaultFamily);
        }

        if (StringUtil.isEmptyString(rowKeyHandlerName)) {
            rowKeyHandlerName = BytesRowKeyHandler.class.getCanonicalName();
        }

        rowKeyHandler = RowKeyHandlerHolder.findRowKeyHandler(rowKeyHandlerName);

        if (hBaseColoumSchemas.isEmpty()) {
            throw new MyHBaseException("no HBaseColoumSchemas");
        }

        for (HBaseColoumSchema coloumSchema : hBaseColoumSchemas) {
            if (StringUtil.isEmptyString(coloumSchema.getFamily())) {
                coloumSchema.setFamily(defaultFamily);
            }
            coloumSchema.init();

            // family -> HBaseColoumSchema
            Map<String, HBaseColoumSchema> tmpMap = coloumSchemas.get(coloumSchema.getQualifier());
            if (tmpMap == null) {
                tmpMap = new TreeMap<String, HBaseColoumSchema>();
                coloumSchemas.put(coloumSchema.getQualifier(), tmpMap);
            }
            tmpMap.put(coloumSchema.getFamily(), coloumSchema);
        }
    }

}
