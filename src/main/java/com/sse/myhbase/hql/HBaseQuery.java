package com.sse.myhbase.hql;

import com.sse.myhbase.util.Util;

/**
 * @Author: Cai Shunda
 * @Description:
 * @Date: Created in 19:13 2017/11/11
 * @Modified by:
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
