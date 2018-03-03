package com.sse.myhbase.hql.node.unary;

import com.sse.myhbase.hql.HQLNode;
import org.w3c.dom.Node;

/**
 * @author: Cai Shunda
 * @description:
 * @date: Created in 16:27 2018/3/1
 * @modified by:
 */
public class IsNotPropertyAvailableNodeHandler extends UnaryNodeHandler{
    @Override
    public HQLNode handle(Node node) {
        IsNotPropertyAvailableNode isNotPropertyAvailableNode = new IsNotPropertyAvailableNode();
        super.handle(isNotPropertyAvailableNode, node);
        return isNotPropertyAvailableNode;
    }
}
