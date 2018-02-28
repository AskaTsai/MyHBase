package com.sse.myhbase.hql;

import com.sse.myhbase.hql.node.text.CDATASectionNodeHandler;
import com.sse.myhbase.hql.node.text.CommentNodeHandler;
import com.sse.myhbase.hql.node.StatementNodeHandler;
import com.sse.myhbase.hql.node.text.TextNodeHandler;
import com.sse.myhbase.util.Util;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Cai Shunda
 * @description: HQL的解析器
 * @date: Created in 22:13 2017/11/11
 * @modified by:
 */
public class HQLNodeParser {

    private static Map<HQLNodeType, HQLNodeHandler> hqlNodeHandlerMap = new HashMap<>();

    private static void register(HQLNodeType hqlNodeType, HQLNodeHandler hqlNodeHandler) {
        hqlNodeHandlerMap.put(hqlNodeType, hqlNodeHandler);
    }

    static {
        register(HQLNodeType.Statement, new StatementNodeHandler());
        register(HQLNodeType.Text, new TextNodeHandler());
        register(HQLNodeType.Comment, new CommentNodeHandler());
        register(HQLNodeType.CDATASection, new CDATASectionNodeHandler());

        register(HQLNodeType.Dynamic, new DynamicNodeHandler());
    }

    /**
     * @author: Cai Shunda
     * @description: 把hql从xml节点转化成hql自定义图节点
     * @date: 19:35 2017/11/13
     */
    public static HQLNode parse(Node node){
        Util.checkNull(node);

        HQLNodeType hqlNodeType = HQLNodeType.findHQLNodeType(node);
        HQLNodeHandler hqlNodeHandler = hqlNodeHandlerMap.get(hqlNodeType);
        HQLNode hqlNode = hqlNodeHandler.handle(node);

        //处理子节点，并进行组合成树
        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node subNode = nodeList.item(i);
            HQLNode subHqlNode = parse(subNode);
            subHqlNode.setParent(hqlNode);
            hqlNode.addSubHQLNode(subHqlNode);
        }
        return hqlNode;
    }
}
