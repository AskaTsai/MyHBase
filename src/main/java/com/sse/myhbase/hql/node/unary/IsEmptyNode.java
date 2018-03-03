package com.sse.myhbase.hql.node.unary;

import com.sse.myhbase.config.MyHBaseRuntimeSetting;
import com.sse.myhbase.exception.MyHBaseException;
import com.sse.myhbase.hql.HQLNodeType;

import java.util.Collection;
import java.util.Map;

/**
 * @author: Cai Shunda
 * @description:
 * @date: Created in 13:19 2018/3/1
 * @modified by:
 */
public class IsEmptyNode extends UnaryNode{
    protected IsEmptyNode() {
        super(HQLNodeType.IsEmpty);
    }
    @Override
    protected boolean isConditionSatisfied(Map<String, Object> para, MyHBaseRuntimeSetting myHBaseRuntimeSetting) {
        if (para.containsKey(getProperty())) {
            Object value = para.get(getProperty());
            if (value == null) {
                return true;
            }
            if (value instanceof String) {
                return String.class.cast(value).isEmpty();
            }
            if (value instanceof Collection) {
                return Collection.class.cast(value).isEmpty();
            }
        }
        throw new MyHBaseException("IsEmptyNode invalid value.");
    }
}
