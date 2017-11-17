package com.sse.myhbase.client.service;

import com.sse.myhbase.config.HBaseTableConfig;

/**
 * @Author: Cai Shunda
 * @Description: 用户可以透过当前客户端获取或者设置HBaseTableConfig
 * @Date: Created in 14:59 2017/11/13
 * @Modified by:
 */
public interface HBaseTableConfigAware {
    public HBaseTableConfig getHBaseTableConfig();
    public void setHBaseTableConfig(HBaseTableConfig hBaseTableConfig);
    /**
     * @Author: Cai Shunda
     * @Description: 是否需要自动创建表
     * @Date: 19:53 2017/11/17
     */
    public boolean isAutoCreate();

    public void autoCreateTable();

}
