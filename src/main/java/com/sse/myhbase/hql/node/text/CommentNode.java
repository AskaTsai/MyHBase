package com.sse.myhbase.hql.node.text;

import com.sse.myhbase.config.MyHBaseRuntimeSetting;
import com.sse.myhbase.hql.HQLNodeType;

import java.util.Map;

/**
 * @author: Cai Shunda
 * @description:
 * @date: Created in 14:50 2018/2/28
 * @modified by:
 */
public class CommentNode extends BaseTextNode{

    protected CommentNode() {
        super(HQLNodeType.Comment);
    }


    @Override
    public void applyParaMap(Map<String, Object> para, StringBuilder sb, Map<Object, Object> context, MyHBaseRuntimeSetting runtimeSetting) {
        //不做任何事情
    }
}
