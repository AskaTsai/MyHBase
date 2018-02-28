package com.sse.myhbase.hql.node;

import com.sse.myhbase.hql.HQLNode;
import com.sse.myhbase.hql.HQLNodeHandler;
import org.w3c.dom.Node;

/**
 * @author: Cai Shunda
 * @description:
 * @date: Created in 14:20 2018/1/7
 * @modified by:
 */
public class StatementNodeHandler implements HQLNodeHandler{
    @Override
    public HQLNode handle(Node node) {
        StatementNode statementNode = new StatementNode();
        return statementNode;
    }
}
