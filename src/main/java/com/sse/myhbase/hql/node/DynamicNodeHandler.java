package com.sse.myhbase.hql.node;

import com.sse.myhbase.hql.HQLNode;
import org.w3c.dom.Node;

/**
 * @author: Cai Shunda
 * @description:
 * @date: Created in 20:46 2018/2/28
 * @modified by:
 */
public class DynamicNodeHandler extends PrependNodeHandler{
    @Override
    public HQLNode handle(Node node) {
        DynamicNode dynamicNode = new DynamicNode();
        super.handle(dynamicNode, node);
        return dynamicNode;
    }
}
