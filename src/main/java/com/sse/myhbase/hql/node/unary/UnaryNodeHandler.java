package com.sse.myhbase.hql.node.unary;

import com.sse.myhbase.hql.node.PrependNodeHandler;
import com.sse.myhbase.util.XmlUtil;
import org.w3c.dom.Node;

/**
 * @author: Cai Shunda
 * @description: 一元节点
 * @date: Created in 22:01 2018/2/28
 * @modified by:
 */
abstract public class UnaryNodeHandler extends PrependNodeHandler {

    public void handle(UnaryNode unaryNode, Node node) {
        unaryNode.setProperty(XmlUtil.getAttribute(node, "property"));
        super.handle(unaryNode, node);
    }
}
