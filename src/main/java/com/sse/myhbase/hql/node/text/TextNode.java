package com.sse.myhbase.hql.node.text;

import com.sse.myhbase.config.MyHBaseRuntimeSetting;
import com.sse.myhbase.hql.HQLNodeType;

import java.util.Map;

/**
 * @author: Cai Shunda
 * @description:
 * @date: Created in 13:42 2018/2/28
 * @modified by:
 */
public class TextNode  extends BaseTextNode{
    protected TextNode() {
        super(HQLNodeType.Text);
    }

    @Override
    public void applyParaMap(Map<String, Object> para, StringBuilder sb, Map<Object, Object> context, MyHBaseRuntimeSetting runtimeSetting) {
        if (getTextValue() != null) {
            sb.append(BlankSpace);
            sb.append(getTextValue());
        }
    }
}
