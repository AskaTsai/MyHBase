package com.sse.myhbase.hql.node.text;

import com.sse.myhbase.hql.HQLNode;
import org.w3c.dom.Node;

/**
 * @author: Cai Shunda
 * @description:
 * @date: Created in 14:44 2018/2/28
 * @modified by:
 */
public class CommentNodeHandler extends BaseTextNodeHandler{
    @Override
    public HQLNode handle(Node node) {
        CommentNode commentNode = new CommentNode();
        super.handle(commentNode, node);
        return commentNode;
    }
}
