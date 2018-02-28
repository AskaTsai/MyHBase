package com.sse.myhbase.hql.node;

import com.sse.myhbase.config.MyHBaseRuntimeSetting;
import com.sse.myhbase.hql.HQLNode;
import com.sse.myhbase.hql.HQLNodeType;
import com.sse.myhbase.util.Util;

import java.util.Map;

/**
 * @author: Cai Shunda
 * @description:
 * @date: Created in 14:24 2018/1/7
 * @modified by:
 */
public class StatementNode extends HQLNode {

    protected StatementNode() {
        super(HQLNodeType.Statement);
    }

    @Override
    public void applyParaMap(Map<String, Object> para, StringBuilder sb, Map<Object, Object> context, MyHBaseRuntimeSetting runtimeSetting) {
        Util.checkNull(sb);
        Util.checkNull(context);
        Util.checkNull(runtimeSetting);

        for (HQLNode hqlNode : subNodeList) {
            hqlNode.applyParaMap(para, sb, context, runtimeSetting);
        }
    }
}
