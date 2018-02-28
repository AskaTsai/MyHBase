package com.sse.myhbase.hql.node.text;

import com.sse.myhbase.hql.HQLNode;
import org.w3c.dom.Node;

/**
 * @author: Cai Shunda
 * @description:
 * @date: Created in 20:35 2018/2/28
 * @modified by:
 */
public class CDATASectionNodeHandler extends BaseTextNodeHandler{

    @Override
    public HQLNode handle(Node node) {
        CDATASectionNode cdataSectionNode = new CDATASectionNode();
        super.handle(cdataSectionNode, node);
        return cdataSectionNode;
    }
}
