package com.sse.myhbase.hql;

import com.sse.myhbase.exception.MyHBaseException;
import com.sse.myhbase.util.Util;
import org.w3c.dom.Node;

/**
 * @author: Cai Shunda
 * @description: HQL节点的类型
 * @date: Created in 13:51 2018/1/7
 * @modified by:
 */
public enum HQLNodeType {
    //hql的最顶层节点
    Statement("statement"),

    //文本节点，DOM树包含了被称为“不可忽略的空白”的文本节点，它是标签之间的空白（如回车符）。
    //多出来的那些Text内容是(包括，但不限于)一个结束标签与另一个开头标签的之间的空白部分
    Text("#text"),

    //
    Comment("#comment"),

    //xml防止转义部件<![CDATA[ 内容 ]]>
    CDATASection("#cdata-section"),

    //dynamic节点
    Dynamic("dynamic");
    /**
     * xml节点的的名称
     */
    private String xmlNodeName;

    HQLNodeType(String xmlNodeName) {
        this.xmlNodeName = xmlNodeName;
    }

    public static HQLNodeType findHQLNodeType(Node node) {
        Util.checkNull(node);

        for (HQLNodeType hqlNodeType : HQLNodeType.values()) {
            if (node.getNodeName().equals(hqlNodeType.xmlNodeName)) {
                return hqlNodeType;
            }
        }

        throw new MyHBaseException("parse error. node = " + node);
    }
}