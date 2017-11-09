package com.sse.myhbase.config;

import com.sse.myhbase.Type.DefaultTypeHandlers;
import com.sse.myhbase.Type.TypeHandler;
import com.sse.myhbase.Type.TypeHandlerHolder;
import com.sse.myhbase.exception.MyHBaseException;
import com.sse.myhbase.util.ClassUtil;
import com.sse.myhbase.util.StringUtil;
import com.sse.myhbase.util.Util;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

/**
 * @Author: Cai Shunda
 * @Description: HBase表列的schema，包含列的所有元数据
 * @Date: Created in 20:40 2017/11/7
 * @Modified by:
 */
public class HBaseColoumSchema {
    /**
     *logger
     */
    private static Logger logger = Logger.getLogger(HBaseColoumSchema.class);

    //----------------xml的配置----------------------
    /**
     *hbase的列族
     */
    @ConfigAttr
    private String family;

    /**
     * hbase的列名
     * */
    @ConfigAttr
    private String qualifier;

    /**
     * 该列值的类型名（java type）
     */
    @ConfigAttr
    private String typeName;

    /**
     * typeHandler的名字
     */
    @ConfigAttr
    private String typeHandlerName;

    //------------------运行时的配置-----------------------
    /**
     *hbase的列族bytes
     */
    private byte[] familyBytes;

    /**
     * hbase的列名bytes
     * */
    private byte[] qualifierBytes;

    /**
     *  列值的java类型
     */
    private Class<?> type;

    /**
     * typeHandler实例
     */
    private TypeHandler typeHandler;

    public void init() {
        StringUtil.isEmptyString(family);
        StringUtil.isEmptyString(qualifier);
        StringUtil.isEmptyString(typeName);

        try {
            familyBytes = Bytes.toBytes(family);
            qualifierBytes = Bytes.toBytes(qualifier);
            type = ClassUtil.forName(typeName);
            Util.checkNull(type);

            if (StringUtil.isEmptyString(typeHandlerName)) {
                typeHandler = DefaultTypeHandlers.findDefaultTypeHandler(type);
                typeHandlerName = typeName.getClass().getName();
            } else {
                typeHandler = TypeHandlerHolder.findTypeHandler(typeHandlerName);
            }

            Util.checkNull(typeHandler);
            Util.checkNull(typeHandlerName);

        } catch (Exception e) {
            logger.error("init coloum schema error", e);
            throw new MyHBaseException("init coloum schema error", e);
        }

    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getQualifier() {
        return qualifier;
    }

    public void setQualifier(String qualifier) {
        this.qualifier = qualifier;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeHandlerName() {
        return typeHandlerName;
    }

    public void setTypeHandlerName(String typeHandlerName) {
        this.typeHandlerName = typeHandlerName;
    }

    public byte[] getFamilyBytes() {
        return familyBytes;
    }

    public byte[] getQualifierBytes() {
        return qualifierBytes;
    }

    public Class<?> getType() {
        return type;
    }

    public TypeHandler getTypeHandler() {
        return typeHandler;
    }
}
