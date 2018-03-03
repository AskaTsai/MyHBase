package com.sse.myhbase.hql.node.binary;

import com.sse.myhbase.hql.HQLNode;
import org.w3c.dom.Node;

/**
 * @author: Cai Shunda
 * @description:
 * @date: Created in 19:34 2018/3/2
 * @modified by:
 */
public class IsGreaterEqualNodeHandler extends BinaryNodeHandler{
    @Override
    public HQLNode handle(Node node) {
        IsGreaterEqualNode isGreaterEqualNode = new IsGreaterEqualNode();
        super.handle(isGreaterEqualNode, node);
        return isGreaterEqualNode;
    }
}
