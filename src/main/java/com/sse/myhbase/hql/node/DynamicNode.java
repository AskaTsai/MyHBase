package com.sse.myhbase.hql.node;

import com.sse.myhbase.config.MyHBaseRuntimeSetting;
import com.sse.myhbase.hql.HQLNode;
import com.sse.myhbase.hql.HQLNodeType;

import java.util.Map;

/**
 * @author: Cai Shunda
 * @description:
 * @date: Created in 21:00 2018/2/28
 * @modified by:
 */
public class DynamicNode extends PrependNode{
    protected DynamicNode() {
        super(HQLNodeType.Dynamic);
    }
    @Override
    public void applyParaMap(Map<String, Object> para, StringBuilder sb, Map<Object, Object> context, MyHBaseRuntimeSetting runtimeSetting) {
        for (HQLNode hqlNode : subNodeList) {
            hqlNode.applyParaMap(para, sb, context, runtimeSetting);
        }
    }
}
