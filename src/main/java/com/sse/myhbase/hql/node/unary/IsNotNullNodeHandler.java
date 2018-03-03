package com.sse.myhbase.hql.node.unary;

import com.sse.myhbase.hql.HQLNode;
import org.w3c.dom.Node;

/**
 * @author: Cai Shunda
 * @description:
 * @date: Created in 13:13 2018/3/1
 * @modified by:
 */
public class IsNotNullNodeHandler extends UnaryNodeHandler{

    @Override
    public HQLNode handle(Node node) {
        IsNotNullNode isNotNullNode = new IsNotNullNode();
        super.handle(isNotNullNode, node);
        return isNotNullNode;
    }
}
