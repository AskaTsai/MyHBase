package com.sse.myhbase.hql.node.unary;

import com.sse.myhbase.hql.HQLNode;
import org.w3c.dom.Node;

/**
 * @author: Cai Shunda
 * @description:
 * @date: Created in 13:18 2018/3/1
 * @modified by:
 */
public class IsEmptyNodeHandler extends UnaryNodeHandler{
    @Override
    public HQLNode handle(Node node) {
        IsEmptyNode isEmptyNode = new IsEmptyNode();
        super.handle(isEmptyNode, node);
        return isEmptyNode;
    }
}
