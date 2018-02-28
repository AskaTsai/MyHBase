package com.sse.myhbase.hql.node.text;

import com.sse.myhbase.hql.HQLNode;
import org.w3c.dom.Node;

/**
 * @author: Cai Shunda
 * @description:
 * @date: Created in 13:14 2018/2/28
 * @modified by:
 */
public class TextNodeHandler extends BaseTextNodeHandler {


    @Override
    public HQLNode handle(Node node) {
        TextNode textNode = new TextNode();
        super.handle(textNode, node);
        return textNode;
    }
}
