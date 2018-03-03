package com.sse.myhbase.hql.node.unary;

import com.sse.myhbase.hql.HQLNodeType;
import com.sse.myhbase.hql.node.ConditionNode;

/**
 * @author: Cai Shunda
 * @description:
 * @date: Created in 22:07 2018/2/28
 * @modified by:
 */
abstract public class UnaryNode extends ConditionNode {
    private String property;

    protected UnaryNode(HQLNodeType hqlNodeType) {
        super(hqlNodeType);
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }
}
