package com.sse.myhbase.hql.node.binary;

import com.sse.myhbase.config.MyHBaseRuntimeSetting;
import com.sse.myhbase.exception.MyHBaseException;
import com.sse.myhbase.hql.HQLNodeType;
import com.sse.myhbase.util.CompareUtil;

import java.util.Map;

/**
 * @author: Cai Shunda
 * @description:
 * @date: Created in 13:50 2018/3/2
 * @modified by:
 */
public class IsGreaterThanNode extends BinaryNode {

    protected IsGreaterThanNode() {
        super(HQLNodeType.IsGreaterThan);
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
        System.out.println("propertyvalue=" + propertyValue + "， compareValue=" + compareValue);
        return CompareUtil.compare(propertyValue, compareValue) > 0;
    }
}
