package com.sse.myhbase.hql;

import org.w3c.dom.Node;

/**
 * @author: Cai Shunda
 * @description: HQL节点的处理器
 * @date: Created in 14:07 2018/1/7
 * @modified by:
 */
public interface HQLNodeHandler {

    /**
     * @Author: Cai Shunda
     * @Description: 把DOM节点转化成HQL节点
     * @Param:
     * @Date: 14:09 2018/1/7
     */
    public HQLNode handle(Node node);
}
