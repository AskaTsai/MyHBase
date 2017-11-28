package com.sse.myhbase.client.service;

import com.sse.myhbase.config.HBaseTableConfig;

/**
 * @author: Cai Shunda
 * @description: 用户可以透过当前客户端获取或者设置HBaseTableConfig
 * @date: Created in 14:59 2017/11/13
 * @modified by:
 */
public interface HBaseTableConfigAware {
    public HBaseTableConfig getHBaseTableConfig();
    public void setHBaseTableConfig(HBaseTableConfig hBaseTableConfig);
    /**
     * @author: Cai Shunda
     * @description: 是否需要自动创建表
     * @date: 19:53 2017/11/17
     */
    public boolean isAutoCreate();

    public void autoCreateTable();

}
