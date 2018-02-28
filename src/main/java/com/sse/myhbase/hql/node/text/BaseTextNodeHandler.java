package com.sse.myhbase.hql.node.text;

import com.sse.myhbase.hql.HQLNodeHandler;
import org.w3c.dom.Node;

/**
 * @author: Cai Shunda
 * @description:
 * @date: Created in 13:16 2018/2/28
 * @modified by:
 */
abstract public class BaseTextNodeHandler implements HQLNodeHandler{

    public void handle(BaseTextNode baseTextNode, Node node) {
        baseTextNode.setTextValue(node.getNodeValue());
    }
}
