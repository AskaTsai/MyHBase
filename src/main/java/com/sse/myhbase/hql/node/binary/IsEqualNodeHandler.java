package com.sse.myhbase.hql.node.binary;

import com.sse.myhbase.hql.HQLNode;
import org.w3c.dom.Node;

/**
 * @author: Cai Shunda
 * @description:
 * @date: Created in 17:06 2018/3/1
 * @modified by:
 */
public class IsEqualNodeHandler extends BinaryNodeHandler{
    @Override
    public HQLNode handle(Node node) {
        IsEqualNode isEqualNode = new IsEqualNode();
        super.handle(isEqualNode, node);
        return isEqualNode;
    }
}
