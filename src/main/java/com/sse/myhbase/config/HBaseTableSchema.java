package com.sse.myhbase.config;

import com.sse.myhbase.client.rowkey.handler.BytesRowKeyHandler;
import com.sse.myhbase.client.rowkey.handler.RowKeyHandler;
import com.sse.myhbase.client.rowkey.handler.RowKeyHandlerHolder;
import com.sse.myhbase.core.NotNullable;
import com.sse.myhbase.core.Nullable;
import com.sse.myhbase.exception.MyHBaseException;
import com.sse.myhbase.util.StringUtil;
import com.sse.myhbase.util.Util;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.*;

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
     * 用于设置该表如果HBase没有的话可不可以自动创建 true/false
     */
    @ConfigAttr
    @Nullable
    private String autoCreate;

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
     * 是否自动创建表，默认false
     */
    private boolean isAutoCreate = false;

    /**
     * 列的schema  map的规则Qualifier->Family-> HBaseColumnSchema.
     */
    private Map<String, Map<String, HBaseColumnSchema>> coloumSchemas = new TreeMap<>();

    /**
     * RowKeyHandler的实例
     */
    private RowKeyHandler rowKeyHandler;

    /**
     * 初始化
     */
    public void init(List<HBaseColumnSchema> hBaseColumnSchemas) {
        Util.checkEmptyString(tableName);
        tableNameBytes = Bytes.toBytes(tableName);

        if (StringUtil.isNotEmptyString(defaultFamily)) {
            defaultFamilyBytes = Bytes.toBytes(defaultFamily);
        }

        if (StringUtil.isNotEmptyString(autoCreate)
                && !"true".equalsIgnoreCase(autoCreate)
                && !"false".equalsIgnoreCase(autoCreate)) {
            throw new MyHBaseException("wrong value " + autoCreate + " for attribute isAutoCreate in HBaseTableSchema.");
        }
        if ("true".equalsIgnoreCase(autoCreate)) {
            isAutoCreate = true;
        }

        if (StringUtil.isEmptyString(rowKeyHandlerName)) {
            rowKeyHandlerName = BytesRowKeyHandler.class.getCanonicalName();
        }

        rowKeyHandler = RowKeyHandlerHolder.findRowKeyHandler(rowKeyHandlerName);

        if (hBaseColumnSchemas.isEmpty()) {
            throw new MyHBaseException("no HBaseColoumSchemas");
        }

        for (HBaseColumnSchema coloumSchema : hBaseColumnSchemas) {
            if (StringUtil.isEmptyString(coloumSchema.getFamily())) {
                coloumSchema.setFamily(defaultFamily);
            }
            coloumSchema.init();

            // family -> HBaseColumnSchema
            Map<String, HBaseColumnSchema> tmpMap = coloumSchemas.get(coloumSchema.getQualifier());
            if (tmpMap == null) {
                tmpMap = new TreeMap<String, HBaseColumnSchema>();
                coloumSchemas.put(coloumSchema.getQualifier(), tmpMap);
            }
            tmpMap.put(coloumSchema.getFamily(), coloumSchema);
        }
    }

    /**
     * @Author: Cai Shunda
     * @Description: 通过列名找到对应的列Schema，只有当且仅当通过指定列名找到一个Schema才能返回，否则会报错
     * @Date: 21:48 2017/11/12
     */
    public HBaseColumnSchema findColummSchema(String qualifier) {
        Util.checkEmptyString(qualifier);
        Map<String, HBaseColumnSchema> tem = coloumSchemas.get(qualifier);
        Util.checkNull(tem);
        if (tem.size() == 1) {
            for (HBaseColumnSchema t : tem.values()) {
                return t;
            }
        }
        throw new MyHBaseException("find 0 or more than one HBaseColumnSchema " +
                "with specified qualifier = " + qualifier);
    }

    /**
     * @Author: Cai Shunda
     * @Description: 获取所有配置的列族,去重
     * @Date: 21:17 2017/11/17
     */
    public Set<String> getAllConfigedFamilys() {
        Set<String> familys = new HashSet<>();
        if (StringUtil.isNotEmptyString(defaultFamily)) {
            familys.add(defaultFamily);
        }

        for (Map<String, HBaseColumnSchema> tmp :coloumSchemas.values()) {
            for (String family: tmp.keySet()) {
                familys.add(family);
            }
        }

        return familys;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDefaultFamily() {
        return defaultFamily;
    }

    public void setDefaultFamily(String defaultFamily) {
        this.defaultFamily = defaultFamily;
    }

    public String getAutoCreate() {
        return autoCreate;
    }

    public void setAutoCreate(String autoCreate) {
        this.autoCreate = autoCreate;
    }

    public String getRowKeyHandlerName() {
        return rowKeyHandlerName;
    }

    public void setRowKeyHandlerName(String rowKeyHandlerName) {
        this.rowKeyHandlerName = rowKeyHandlerName;
    }

    public boolean isAutoCreate() {return this.isAutoCreate;}

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n==================HBaseTableConfig==================\n");
        StringUtil.appendKeyValue(sb, null, "tableName", tableName);
        StringUtil.appendKeyValue(sb, null, "defaultFamily", defaultFamily);
        StringUtil.appendKeyValue(sb, null, "rowkeyHandlerName", rowKeyHandlerName);
        for (Map<String, HBaseColumnSchema> tmp :coloumSchemas.values()) {
            for (HBaseColumnSchema columnSchema : tmp.values()) {
                StringUtil.appendKeyValue(sb, null, "columnSchema", columnSchema.toString());
            }
        }
        sb.append("==================HBaseTableConfig==================");
        return sb.toString();
    }
}
