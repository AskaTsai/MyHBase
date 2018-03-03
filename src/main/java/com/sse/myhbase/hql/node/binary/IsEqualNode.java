package com.sse.myhbase.hql.node.binary;

import com.sse.myhbase.config.MyHBaseRuntimeSetting;
import com.sse.myhbase.exception.MyHBaseException;
import com.sse.myhbase.hql.HQLNodeType;

import java.util.Map;

/**
 * @author: Cai Shunda
 * @description:
 * @date: Created in 17:16 2018/3/1
 * @modified by:
 */
public class IsEqualNode extends BinaryNode{

    protected IsEqualNode() {
        super(HQLNodeType.IsEqual);
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
        return propertyValue.equals(compareValue);
    }
}
