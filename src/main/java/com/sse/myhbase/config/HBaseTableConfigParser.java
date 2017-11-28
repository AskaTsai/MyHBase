package com.sse.myhbase.config;

import com.sse.myhbase.hql.HBaseQuery;
import com.sse.myhbase.hql.HQLNodeParser;
import com.sse.myhbase.util.Util;
import com.sse.myhbase.util.XmlUtil;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Cai Shunda
 * @description: HBaseTableConfi的解析器
 * @date: Created in 19:28 2017/11/11
 * @modified by:
 */
public class HBaseTableConfigParser {

    /**
     * @author: Cai Shunda
     * @description: 从xml配置中解析出HBase表的Schema（只能解析静态数据）
     * @date: 21:38 2017/11/11
     */
    public static void parseTableSchema(InputStream inputStream, HBaseTableSchema tableSchema,
                                  List<HBaseColumnSchema> hbaseColumnSchemas) {
        Util.checkNull(inputStream);
        Util.checkNull(tableSchema);
        Util.checkNull(hbaseColumnSchemas);

        //HBaseTableSchema
        Node hbaseTableSchemaNode = XmlUtil.findTopLevelNode(inputStream, "HBaseTableSchema");
        tableSchema.setTableName(XmlUtil.getAttribute(hbaseTableSchemaNode, "tableName"));
        tableSchema.setDefaultFamily(XmlUtil.getAttribute(hbaseTableSchemaNode, "defaultFamily"));
        tableSchema.setRowKeyHandlerName(XmlUtil.getAttribute(hbaseTableSchemaNode, "rowKeyHandlerName"));
        tableSchema.setAutoCreate(XmlUtil.getAttribute(hbaseTableSchemaNode, "isAutoCreate"));

        //HBaseColumnSchema
        NodeList nodeList = hbaseTableSchemaNode.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (!"HBaseColumnSchema".equals(node.getNodeName())) {
                continue;
            }
            HBaseColumnSchema hBaseColumnSchema = new HBaseColumnSchema();
            hBaseColumnSchema.setFamily(XmlUtil.getAttribute(node, "family"));
            hBaseColumnSchema.setQualifier(XmlUtil.getAttribute(node, "qualifier"));
            hBaseColumnSchema.setTypeName(XmlUtil.getAttribute(node, "typeName"));
            hBaseColumnSchema.setTypeHandlerName(XmlUtil.getAttribute(node, "handler"));
            hbaseColumnSchemas.add(hBaseColumnSchema);
        }
    }

    /**
     * @author: Cai Shunda
     * @description: 解析文件得到xml中配置的HBase查询
     * @date: 21:45 2017/11/11
     */
    public static List<HBaseQuery> parseHBaseQuery(InputStream inputStream) {
        Util.checkNull(inputStream);
        Node statementsNode = XmlUtil.findTopLevelNode(inputStream, "statements");
        return parseHBaseQueryList(statementsNode);
    }

    /**
     * @author: Cai Shunda
     * @description: 解析文件得到xml中配置的HBase查询
     * @date: 21:45 2017/11/11
     */
    private static List<HBaseQuery> parseHBaseQueryList(Node statementsNode) {
        List<HBaseQuery> hBaseQueries = new ArrayList<>();
        if (statementsNode == null) {
            return hBaseQueries;
        }

        NodeList nodeList = statementsNode.getChildNodes();
        if (nodeList == null || nodeList.getLength() == 0) {
            return hBaseQueries;
        }
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (!node.getNodeName().equals("statement")) {
                continue;
            }
            String id = XmlUtil.getAttribute(node, "id");
            HBaseQuery hBaseQuery = new HBaseQuery(id, HQLNodeParser.parse(node));
            hBaseQueries.add(hBaseQuery);
        }

        return hBaseQueries;
    }
}
