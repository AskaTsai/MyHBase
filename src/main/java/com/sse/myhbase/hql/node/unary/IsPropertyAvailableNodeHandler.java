package com.sse.myhbase.hql.node.unary;

import com.sse.myhbase.hql.HQLNode;
import org.w3c.dom.Node;

/**
 * @author: Cai Shunda
 * @description:
 * @date: Created in 16:20 2018/3/1
 * @modified by:
 */
public class IsPropertyAvailableNodeHandler extends UnaryNodeHandler{

    @Override
    public HQLNode handle(Node node) {
        IsPropertyAvailableNode isPropertyAvailableNode = new IsPropertyAvailableNode();
        super.handle(isPropertyAvailableNode, node);
        return isPropertyAvailableNode;
    }
}
