package com.sse.myhbase.hql.node;

import com.sse.myhbase.hql.HQLNode;
import com.sse.myhbase.hql.HQLNodeHandler;
import com.sse.myhbase.util.XmlUtil;
import org.w3c.dom.Node;

/**
 * @author: Cai Shunda
 * @description: 前置
 * @date: Created in 20:46 2018/2/28
 * @modified by:
 */
abstract class PrependNodeHandler implements HQLNodeHandler{

    public void handle(PrependNode prependNode, Node node) {
        prependNode.setPrependValue(XmlUtil.getAttribute(node, "prepend"));
    }
}
