package com.sse.myhbase.hql.node.unary;

import com.sse.myhbase.config.MyHBaseRuntimeSetting;
import com.sse.myhbase.hql.HQLNodeType;

import java.util.Map;

/**
 * @author: Cai Shunda
 * @description:
 * @date: Created in 22:17 2018/2/28
 * @modified by:
 */
public class IsNullNode extends UnaryNode{
    protected IsNullNode() {
        super(HQLNodeType.IsNull);
    }

    @Override
    protected boolean isConditionSatisfied(Map<String, Object> para, MyHBaseRuntimeSetting myHBaseRuntimeSetting) {
        return para.containsKey(getProperty()) && para.get(getProperty()) == null;
    }
}
