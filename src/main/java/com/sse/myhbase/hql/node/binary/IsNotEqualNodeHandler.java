package com.sse.myhbase.hql.node.binary;

import com.sse.myhbase.hql.HQLNode;
import org.w3c.dom.Node;

/**
 * @author: Cai Shunda
 * @description:
 * @date: Created in 13:38 2018/3/2
 * @modified by:
 */
public class IsNotEqualNodeHandler extends BinaryNodeHandler {
    @Override
    public HQLNode handle(Node node) {
        IsNotEqualNode isNotEqualNode = new IsNotEqualNode();
        super.handle(isNotEqualNode, node);
        return isNotEqualNode;
    }
}
