package com.sse.myhbase.hql;

import com.sse.myhbase.config.MyHBaseRuntimeSetting;
import com.sse.myhbase.core.NotNullable;
import com.sse.myhbase.core.Nullable;
import com.sse.myhbase.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: Cai Shunda
 * @description:
 * @date: Created in 19:35 2017/11/13
 * @modified by:
 */
abstract public class HQLNode {

    //空格
    protected static final String BlankSpace = " ";

    /**
     * 当前节点的父亲节点
     */
    @Nullable
    protected HQLNode parent;

    /**
     * 当前节点的类型
     */
    protected HQLNodeType hqlNodeType;

    /**
     * 当前节点的孩子节点
     */
    protected List<HQLNode> subNodeList = new ArrayList<>();

    /**
     * @Author: Cai Shunda
     * @Description: 赋予HQL以特定参数值，组成最终的HQL语句，sb就是最终的HQL语句,context主要用来记录上下文比如之前处理过什么节点
     * @Param:
     * @Date: 14:33 2018/1/7
     */
    public abstract void applyParaMap(
            @Nullable Map<String, Object> para,
            @NotNullable StringBuilder sb,
            @NotNullable Map<Object, Object> context,
            @NotNullable MyHBaseRuntimeSetting runtimeSetting);

    protected HQLNode(HQLNodeType hqlNodeType) {
        Util.checkNull(hqlNodeType);
        this.hqlNodeType = hqlNodeType;
    }

    public void addSubHQLNode(HQLNode hqlNode) {
        subNodeList.add(hqlNode);
    }
    public HQLNodeType getHqlNodeType() {
        return hqlNodeType;
    }

    public HQLNode getParent() {
        return parent;
    }

    public void setParent(HQLNode parent) {
        this.parent = parent;
    }
}
