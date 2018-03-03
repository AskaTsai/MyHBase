package com.sse.myhbase.hql.node.binary;

import com.sse.myhbase.hql.HQLNodeType;
import com.sse.myhbase.hql.node.ConditionNode;
import org.apache.log4j.Logger;

/**
 * @author: Cai Shunda
 * @description:
 * @date: Created in 17:09 2018/3/1
 * @modified by:
 */
abstract public class BinaryNode extends ConditionNode {

    final private static Logger logger = Logger.getLogger(BinaryNode.class);

    private String property;
    private String compareValue;

    protected BinaryNode(HQLNodeType hqlNodeType) {
        super(hqlNodeType);
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getCompareValue() {
        return compareValue;
    }

    public void setCompareValue(String compareValue) {
        this.compareValue = compareValue;
    }
}
