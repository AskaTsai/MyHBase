package com.sse.myhbase.client;

import com.sse.myhbase.config.HBaseColumnSchema;
import com.sse.myhbase.config.HBaseTableSchema;
import com.sse.myhbase.exception.MyHBaseException;
import com.sse.myhbase.util.ClassUtil;
import com.sse.myhbase.util.StringUtil;
import com.sse.myhbase.util.Util;
import com.sse.myhbase.util.XmlUtil;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Cai Shunda
 * @description: POJO类型和HBase表映射信息
 * @date: Created in 19:17 2017/11/11
 * @modified by:
 */
public class TypeInfo {
    /**
     * pojo的类型
     */
    private Class<?> type;
    /**
     * pojo的列信息列表
     */
    private List<ColumnInfo> columnInfos = new ArrayList<>();
    /**
     * 列的版本信息
     */
    private ColumnInfo versionedColumnInfo;
    /**
     * qualifier -> familiy -> ColumnInfo
     */
    private Map<String, Map<String, ColumnInfo>> columnInfoMap = new HashMap<String, Map<String, ColumnInfo>>();

    /**
     * @author: Cai Shunda
     * @description: 从XML中的Node中解析出类型信息
     * @date: 17:32 2017/11/12
     */
    public static TypeInfo parseNode(Node node, HBaseTableSchema hBaseTableSchema) {
        Util.checkNull(node);
        Util.checkNull(hBaseTableSchema);

        TypeInfo typeInfo = new TypeInfo();

        String typeName = XmlUtil.getAttribute(node, "className");
        if (StringUtil.isEmptyString(typeName)) {
            throw new MyHBaseException("No className attribute in Mappingtype.");
        }

        Class<?> type = ClassUtil.forName(typeName);
        typeInfo.type = type;

        String defaultFamily = XmlUtil.getAttribute(node, "defaultFamily");
        NodeList nodeList = node.getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node fieldNode = nodeList.item(i);
            ColumnInfo columnInfo = ColumnInfo.parseNode(type, fieldNode, hBaseTableSchema, defaultFamily);
            if (columnInfo == null) {
                continue;
            }
            typeInfo.columnInfos.add(columnInfo);
        }
        typeInfo.init();
        return typeInfo;
    }

    /**
     * @author: Cai Shunda
     * @description: 根据java对象的class类型直接解析出TypeInfo
     * @Param:
     * @date: 16:53 2017/11/20
     */
    public static TypeInfo parse(Class<?> type) {
        Util.checkNull(type);

        TypeInfo typeInfo = new TypeInfo();
        typeInfo.type = type;

        Field[] fields = type.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            ColumnInfo columnInfo = ColumnInfo.parse(type, field);
            if (columnInfo == null) {
                continue;
            }
            typeInfo.columnInfos.add(columnInfo);
        }
        typeInfo.init();
        return typeInfo;
    }

    /**
     * @author: Cai Shunda
     * @description: 根据java类型和XML中配置的HBase表信息，通过反射进行两者的映射。
     * @Param:
     * @date: 18:09 2017/11/20
     */
    public static TypeInfo parseInAir(Class<?> type, HBaseTableSchema hBaseTableSchema) {
        Util.checkNull(type);
        Util.checkNull(hBaseTableSchema);

        TypeInfo typeInfo = new TypeInfo();
        typeInfo.type = type;
        Field[] fields = type.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            //static属性不考虑，不做映射
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }

            //使用属性名当作列名
            String qualifier = field.getName();
            HBaseColumnSchema hBaseColumnSchema = hBaseTableSchema.findColummSchema(qualifier);

            ColumnInfo columnInfo = ColumnInfo.parseInAir(type, field, hBaseColumnSchema.getFamily());
            if (columnInfo == null) {
                continue;
            }
            typeInfo.columnInfos.add(columnInfo);
        }

        typeInfo.init();
        return typeInfo;
    }

    /**
     * @author: Cai Shunda
     * @description: 初始化对象
     * @date: 20:32 2017/11/12
     */
    public void init(){
       Util.checkNull(type);
       Util.checkNull(columnInfos);
       Util.check(!columnInfos.isEmpty());

       int versionFieldCounter = 0;
       for (ColumnInfo columnInfo : columnInfos) {
           if (columnInfo.isVersioned) {
               versionFieldCounter ++;
               versionedColumnInfo = columnInfo;
           }
           if (!columnInfoMap.containsKey(columnInfo.qualifier)) {
               columnInfoMap.put(columnInfo.qualifier, new HashMap<String, ColumnInfo>());
           }
           columnInfoMap.get(columnInfo.qualifier).put(columnInfo.family, columnInfo);
       }
        //?
       if (versionFieldCounter > 1) {
           throw new MyHBaseException("more than one versioned fields.");
       }
    }
    //?
    public boolean isVersionedType() {
        return versionedColumnInfo != null;
    }


    public Class<?> getType() {
        return type;
    }

    public List<ColumnInfo> getColumnInfos() {
        return columnInfos;
    }

    public ColumnInfo getVersionedColumnInfo() {
        return versionedColumnInfo;
    }

    /**
     * @author: Cai Shunda
     * @description: 根据family和qualifier查找JOPO的field和HBase列的对应关系
     * @Param:
     * @date: 21:36 2017/11/24
     */
    public ColumnInfo findColumnInfo(String family, String qualifier) {
        Util.checkEmptyString(family);
        Util.checkEmptyString(qualifier);
        return columnInfoMap.get(qualifier).get(family);
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n==============" + this.getClass() + "==============\n");
        StringUtil.appendKeyValue(sb, null,"type", type);
        StringUtil.appendKeyValue(sb, null, "versionedColumnInfo", versionedColumnInfo);
        for (ColumnInfo columnInfo : columnInfos) {
            StringUtil.appendKeyValue(sb, null, "field", columnInfo);
        }
        sb.append("=============" + this.getClass() + "==============");

        return sb.toString();
    }
}
