package com.sse.myhbase.hql.node.binary;

import com.sse.myhbase.hql.HQLNode;
import org.w3c.dom.Node;

/**
 * @author: Cai Shunda
 * @description:
 * @date: Created in 19:42 2018/3/2
 * @modified by:
 */
public class IsLessEqualNodeHandler extends BinaryNodeHandler{
    @Override
    public HQLNode handle(Node node) {
        IsLessEqualNode isLessEuqalNode = new IsLessEqualNode();
        super.handle(isLessEuqalNode, node);
        return isLessEuqalNode;
    }
}
