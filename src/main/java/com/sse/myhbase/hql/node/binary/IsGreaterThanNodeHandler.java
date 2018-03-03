package com.sse.myhbase.hql.node.binary;

import com.sse.myhbase.hql.HQLNode;
import org.w3c.dom.Node;

/**
 * @author: Cai Shunda
 * @description:
 * @date: Created in 13:47 2018/3/2
 * @modified by:
 */
public class IsGreaterThanNodeHandler extends BinaryNodeHandler{

    @Override
    public HQLNode handle(Node node) {
        IsGreaterThanNode isGreaterThanNode = new IsGreaterThanNode();
        super.handle(isGreaterThanNode, node);
        return isGreaterThanNode;
    }
}
