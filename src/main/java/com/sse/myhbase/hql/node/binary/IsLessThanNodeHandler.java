package com.sse.myhbase.hql.node.binary;

import com.sse.myhbase.hql.HQLNode;
import org.w3c.dom.Node;

/**
 * @author: Cai Shunda
 * @description:
 * @date: Created in 19:41 2018/3/2
 * @modified by:
 */
public class IsLessThanNodeHandler extends BinaryNodeHandler{
    @Override
    public HQLNode handle(Node node) {
        IsLessThanNode isLessThanNode = new IsLessThanNode();
        super.handle(isLessThanNode, node);
        return isLessThanNode;
    }
}
