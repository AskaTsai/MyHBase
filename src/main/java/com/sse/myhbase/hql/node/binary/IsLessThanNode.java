package com.sse.myhbase.hql.node.binary;

import com.sse.myhbase.config.MyHBaseRuntimeSetting;
import com.sse.myhbase.exception.MyHBaseException;
import com.sse.myhbase.hql.HQLNodeType;
import com.sse.myhbase.util.CompareUtil;

import java.util.Map;

/**
 * @author: Cai Shunda
 * @description:
 * @date: Created in 19:44 2018/3/2
 * @modified by:
 */
public class IsLessThanNode extends BinaryNode{
    protected IsLessThanNode() {
        super(HQLNodeType.IsLessThan);
    }
    @Override
    protected boolean isConditionSatisfied(Map<String, Object> para, MyHBaseRuntimeSetting myHBaseRuntimeSetting) {
        Object propertyValue = para.get(getProperty());
        if (propertyValue == null) {
            if (getProperty() == null) {
                throw new MyHBaseException("isEqual Node has no property attribute.");
            } else {
                throw new MyHBaseException("isEqual Node property=" + getProperty() + " has no specified value in code." );
            }
        }
        Object compareValue = myHBaseRuntimeSetting.interpret(propertyValue.getClass(), getCompareValue());

        return CompareUtil.compare(propertyValue, compareValue) < 0;
    }
}
