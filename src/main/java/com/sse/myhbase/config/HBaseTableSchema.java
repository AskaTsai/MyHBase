package com.sse.myhbase.config;

import com.sse.myhbase.core.NotNullable;
import com.sse.myhbase.core.Nullable;

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

    private byte[] tableNameBytes;

    private byte[] defaultFamilyBytes;

    private Map<String, Map<String, HBaseColoumSchema>> coloumSchemas = new TreeMap<>();

    private RowKeyHandler rowKeyHandler;

}
