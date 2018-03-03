package com.sse.myhbase.hql.node.unary;

import com.sse.myhbase.config.MyHBaseRuntimeSetting;
import com.sse.myhbase.hql.HQLNodeType;

import java.util.Map;

/**
 * @author: Cai Shunda
 * @description:
 * @date: Created in 13:15 2018/3/1
 * @modified by:
 */
public class IsNotNullNode extends UnaryNode{

    protected  IsNotNullNode() {
        super(HQLNodeType.IsNotNull);
    }
    @Override
    protected boolean isConditionSatisfied(Map<String, Object> para, MyHBaseRuntimeSetting myHBaseRuntimeSetting) {
        return para.containsKey(getProperty()) && para.get(getProperty()) != null;
    }
}
