package com.sse.myhbase.hql.node.unary;

import com.sse.myhbase.hql.HQLNode;
import org.w3c.dom.Node;

/**
 * @author: Cai Shunda
 * @description:
 * @date: Created in 22:00 2018/2/28
 * @modified by:
 */
public class IsNullNodeHandler extends UnaryNodeHandler {


    @Override
    public HQLNode handle(Node node) {
        IsNullNode isNullNode = new IsNullNode();
        super.handle(isNullNode, node);
        return isNullNode;
    }
}
