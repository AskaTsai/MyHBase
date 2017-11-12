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
 * @Author: Cai Shunda
 * @Description: POJO的属性和HBase表的列的对应关系
 * @Date: Created in 20:26 2017/11/12
 * @Modified by:
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
     * @Author: Cai Shunda
     * @Description: 从field节点中解析出列信息
     * @Date: 21:36 2017/11/12
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
}
