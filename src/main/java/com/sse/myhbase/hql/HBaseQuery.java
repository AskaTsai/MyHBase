package com.sse.myhbase.hql;

import com.sse.myhbase.util.Util;

/**
 * @author: Cai Shunda
 * @description:
 * @date: Created in 19:13 2017/11/11
 * @modified by:
 */
public class HBaseQuery {
    private String id;
    private HQLNode hqlNode;

    public HBaseQuery(String id, HQLNode hqlNode) {
        Util.checkEmptyString(id);
        Util.checkNull(hqlNode);
        this.id = id;
        this.hqlNode = hqlNode;
    }

    public String getId() {
        return id;
    }

    public HQLNode getHqlNode() {
        return hqlNode;
    }
}
