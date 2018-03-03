package com.sse.myhbase.hql.node.binary;

import com.sse.myhbase.hql.HQLNode;
import com.sse.myhbase.hql.node.PrependNodeHandler;
import com.sse.myhbase.util.XmlUtil;
import org.w3c.dom.Node;

/**
 * @author: Cai Shunda
 * @description:
 * @date: Created in 17:07 2018/3/1
 * @modified by:
 */
abstract public class BinaryNodeHandler extends PrependNodeHandler{

    public void handle(BinaryNode binaryNode, Node node) {
        binaryNode.setProperty(XmlUtil.getAttribute(node, "property"));
        binaryNode.setCompareValue(XmlUtil.getAttribute(node, "compareValue"));
        super.handle(binaryNode, node);
    }
}
