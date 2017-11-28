package com.sse.myhbase.client;

import com.sse.myhbase.config.HBaseColumnSchema;
import com.sse.myhbase.config.HBaseTableSchema;
import com.sse.myhbase.exception.MyHBaseException;
import com.sse.myhbase.util.StringUtil;
import com.sse.myhbase.util.Util;
import com.sse.myhbase.util.XmlUtil;
import org.apache.hadoop.hbase.util.Bytes;
import org.w3c.dom.Node;

import java.lang.reflect.Field;

/**
 * @author: Cai Shunda
 * @description: POJO的属性和HBase表的列的对应关系
 * @date: Created in 20:26 2017/11/12
 * @modified by:
 */
public class ColumnInfo {
    /** POJO's class type. */
    Class<?> type;
    /** POJO's field. */
    Field field;
    /** hbase's family. */
    String   family;
    /** hbase's family bytes. */
    byte[]   familyBytes;
    /** hbase's qualifier. */
    String   qualifier;
    /** hbase's qualifier bytes. */
    byte[]   qualifierBytes;
    /** isVersioned. */
    boolean  isVersioned;

    /**
     * @author: Cai Shunda
     * @description: 从field节点中解析出列信息(xml方式)
     * @date: 21:36 2017/11/12
     */
    public static ColumnInfo parseNode(Class<?> type, Node fieldNode,
                                       HBaseTableSchema hBaseTableSchema, String defaultFamily) {
        Util.checkNull(fieldNode);
        Util.checkNull(hBaseTableSchema);

        if (!"field".equals(fieldNode.getNodeName())) {
            return null;
        }

        String name = XmlUtil.getAttribute(fieldNode, "name");
        if (StringUtil.isEmptyString(name)) {
            throw new MyHBaseException("Missing name of field in type = " + type);
        }

        Field field = null;
        try {
            field = type.getDeclaredField(name);
            field.setAccessible(true);
        } catch (NoSuchFieldException e) {
            throw new MyHBaseException("can not find specified field '"+ name +"' in type =" + type , e);
        }

        String qualifier = XmlUtil.getAttribute(fieldNode, "qualifier");
        if (StringUtil.isEmptyString(qualifier)) {
            /** 用field的名字当作列名*/
            qualifier = field.getName();
        }

        String family = XmlUtil.getAttribute(fieldNode, "family");
        if (StringUtil.isEmptyString(family)) {
            /** 没有指定family则采用 MappingType节点中指定的默认family*/
            family = defaultFamily;
        }
        if (StringUtil.isEmptyString(family)) {
            /** 连MappingType中都没有指定默认的family，则直接获取跟HBase表中的相同列名的列对应的family*/
            HBaseColumnSchema hBaseColumnSchema = hBaseTableSchema.findColummSchema(qualifier);
            family = hBaseColumnSchema.getFamily();
        }

        String isVersioned = XmlUtil.getAttribute(fieldNode, "isVersioned");

        ColumnInfo columnInfo = new ColumnInfo();
        columnInfo.type = type;
        columnInfo.field = field;
        columnInfo.family = family;
        columnInfo.familyBytes = Bytes.toBytes(family);
        columnInfo.qualifier = qualifier;
        columnInfo.qualifierBytes = Bytes.toBytes(qualifier);
        columnInfo.isVersioned = "true".equals(isVersioned);
        return columnInfo;

    }

    /**
     * @author: Cai Shunda
     * @description: 从java类型定义里面指定属性field中解析出HBase列信息（注解方式）
     * @Param:
     * @date: 17:55 2017/11/20
     */
    public static ColumnInfo parse(Class<?> type, Field field) {
        //defaultFamily
        String defaultFamily = null;
        HBaseTable hBaseTable = type.getAnnotation(HBaseTable.class);
        if (hBaseTable != null) {
            defaultFamily = hBaseTable.defaultFamily();
        }

        //@HBaseTable可省略 @HBaseColumn不可省略
        HBaseColumn hBaseColumn = field.getAnnotation(HBaseColumn.class);
        if (hBaseColumn == null) {
            return null;
        }

        String family = hBaseColumn.family();
        String qualifer = hBaseColumn.qualifier();

        if (StringUtil.isEmptyString(family)) {
            //@HBaseColumn的family为主 @HBaseTable的defaultFamily为辅
            family = defaultFamily;
        }

        if (StringUtil.isEmptyString(family)) {
            throw new MyHBaseException("family is null or empty ,please check @HBaseTable or @ HBaseColumn. type=" + type + ", field=" + field);
        }

        if (StringUtil.isEmptyString(qualifer)) {
            throw new MyHBaseException("family is null or empty ,please check @HBaseTable or @ HBaseColumn. type=" + type + ", field=" + field);
        }

        ColumnInfo columnInfo = new ColumnInfo();
        columnInfo.type = type;
        columnInfo.field = field;
        columnInfo.family = family;
        columnInfo.familyBytes = Bytes.toBytes(family);
        columnInfo.qualifier = qualifer;
        columnInfo.qualifierBytes = Bytes.toBytes(qualifer);
        columnInfo.isVersioned = (field.getAnnotation(HBaseVersion.class) != null);
        return columnInfo;

    }


    /**
     * @author: Cai Shunda
     * @description: 直接创建一个ColumnInfo，前提是能的HBase表的配置中找到对应的配置，这个逻辑应该在调用此方法前就确认
     * @Param:
     * @date: 19:58 2017/11/20
     */
    public static ColumnInfo parseInAir(Class<?> type, Field field, String family) {
        Util.checkEmptyString(family);

        //用属性名当作列名
        String qualifier = field.getName();
        ColumnInfo columnInfo = new ColumnInfo();
        columnInfo.type = type;
        columnInfo.field = field;
        columnInfo.family = family;
        columnInfo.familyBytes = Bytes.toBytes(family);
        columnInfo.qualifier = qualifier;
        columnInfo.qualifierBytes = Bytes.toBytes(qualifier);

        return columnInfo;
    }

    @Override
    public String toString() {
        return "[type=" + type + " field=" + field + " family=" + family
                + " qualifier=" + qualifier + " isVersioned=" + isVersioned + "]";
    }
}
