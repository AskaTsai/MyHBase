package com.sse.myhbase.hql.node;

import com.sse.myhbase.hql.HQLNode;
import com.sse.myhbase.hql.HQLNodeType;

/**
 * @author: Cai Shunda
 * @description:
 * @date: Created in 20:49 2018/2/28
 * @modified by:
 */
abstract public class PrependNode extends HQLNode{
    /**
     * 前置值
     */
    private String prependValue;

    protected PrependNode(HQLNodeType hqlNodeType) {
        super(hqlNodeType);
    }

    public String getPrependValue() {
        return prependValue;
    }

    public void setPrependValue(String prependValue) {
        this.prependValue = prependValue;
    }
}
