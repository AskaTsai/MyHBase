package com.sse.myhbase.hql.node;

import com.sse.myhbase.config.MyHBaseRuntimeSetting;
import com.sse.myhbase.core.NotNullable;
import com.sse.myhbase.hql.HQLNode;
import com.sse.myhbase.hql.HQLNodeType;
import com.sse.myhbase.util.StringUtil;

import java.util.Map;

/**
 * @author: Cai Shunda
 * @description:
 * @date: Created in 22:08 2018/2/28
 * @modified by:
 */
abstract public class ConditionNode extends PrependNode{

    protected ConditionNode(HQLNodeType hqlNodeType) {
        super(hqlNodeType);
    }

    abstract protected boolean isConditionSatisfied(Map<String, Object> para, @NotNullable MyHBaseRuntimeSetting myHBaseRuntimeSetting);

    /**
     * @Author: Cai Shunda
     * @Description:
     * @Param: sb就是最终的HQL语句
     * @Date: 22:04 2018/3/3
     */
    @Override
    final public void applyParaMap(Map<String, Object> para, StringBuilder sb, Map<Object, Object> context, MyHBaseRuntimeSetting runtimeSetting) {
        if (isConditionSatisfied(para, runtimeSetting)) {
            String prepend = getPrependValue();

            /** 跟ibatis一样，如果当前节点是Dynamic节点的子节点，且是第一次符合条件的（如符合非空），
             *  Dynamic节点的prepend值回覆盖当前节点的prepend，保证不会出现where and的情况
             *  <dynamic prepend="where">
                    <isNotNull property="name" prepend="and">
                        name=#name#
                    </isNotNull>
                    <isNotNull property="sex" prepend="and">
                        sex=#sex#
                    </isNotNull>
                </dynamic>
             */
            HQLNode parent = getParent();
            if (parent != null
                    && parent.getHqlNodeType() == HQLNodeType.Dynamic
                    && !context.containsKey(parent)) {
                context.put(parent, "override prepend once");
                String parentPrepend = ((DynamicNode)parent).getPrependValue();
                if (StringUtil.isNotEmptyString(parentPrepend)) {
                    prepend = parentPrepend;
                }
            }
            
            if (prepend != null) {
                sb.append(BlankSpace);
                sb.append(prepend);
            }

            for (HQLNode hqlNode :subNodeList) {
                hqlNode.applyParaMap(para, sb, context, runtimeSetting);
            }
        }
    }
}
