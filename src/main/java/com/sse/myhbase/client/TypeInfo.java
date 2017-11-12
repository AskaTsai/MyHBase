package com.sse.myhbase.client;

import com.sse.myhbase.config.HBaseTableSchema;
import com.sse.myhbase.exception.MyHBaseException;
import com.sse.myhbase.util.ClassUtil;
import com.sse.myhbase.util.StringUtil;
import com.sse.myhbase.util.Util;
import com.sse.myhbase.util.XmlUtil;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Cai Shunda
 * @Description: POJO类型和HBase表映射信息
 * @Date: Created in 19:17 2017/11/11
 * @Modified by:
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
     * @Author: Cai Shunda
     * @Description: 从XML中的Node中解析出类型信息
     * @Date: 17:32 2017/11/12
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
     * @Author: Cai Shunda
     * @Description: 初始化对象
     * @Date: 20:32 2017/11/12
     */
    public void init(){
       Util.checkNull(type);
       Util.checkNull(columnInfos);
       Util.check(!columnInfoMap.isEmpty());

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
}
