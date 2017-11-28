package com.sse.myhbase.client.service;

import com.sse.myhbase.config.HBaseDataSource;

/**
 * @author: Cai Shunda
 * @description: 客户端可以从当前的客户端获取或者设置HBaseDataSource
 * @date: Created in 14:57 2017/11/13
 * @modified by:
 */
public interface HBaseDataSourceAware {
    public HBaseDataSource getHBaseDataSource();
    public void setHBaseDataSource(HBaseDataSource hBaseDataSource);
}
