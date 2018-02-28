package com.sse.myhbase.hql.node.text;

import com.sse.myhbase.hql.HQLNode;
import com.sse.myhbase.hql.HQLNodeType;

/**
 * @author: Cai Shunda
 * @description:
 * @date: Created in 13:22 2018/2/28
 * @modified by:
 */
abstract  public class BaseTextNode extends HQLNode{
    private String textValue;

    protected BaseTextNode(HQLNodeType hqlNodeType) {
        super(hqlNodeType);
    }

    public String getTextValue() {
        return textValue;
    }

    public void setTextValue(String textValue) {
        this.textValue = textValue;
    }
}
