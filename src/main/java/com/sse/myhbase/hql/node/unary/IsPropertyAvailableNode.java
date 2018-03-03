package com.sse.myhbase.hql.node.unary;

import com.sse.myhbase.config.MyHBaseRuntimeSetting;
import com.sse.myhbase.hql.HQLNodeType;

import java.util.Map;

/**
 * @author: Cai Shunda
 * @description:
 * @date: Created in 16:25 2018/3/1
 * @modified by:
 */
public class IsPropertyAvailableNode extends UnaryNode{

    protected IsPropertyAvailableNode() {
        super(HQLNodeType.IsPropertyAvailable);
    }

    @Override
    protected boolean isConditionSatisfied(Map<String, Object> para, MyHBaseRuntimeSetting myHBaseRuntimeSetting) {
        return para.containsKey(getProperty());
    }
}
