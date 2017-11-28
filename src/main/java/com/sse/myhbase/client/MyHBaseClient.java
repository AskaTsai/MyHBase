package com.sse.myhbase.client;

import com.sse.myhbase.client.service.*;

/**
 * @author: Cai Shunda
 * @description: 整个框架的主要入口点
 * @date: Created in 21:51 2017/11/1
 * @modified by:
 */
public interface MyHBaseClient extends HBaseTableConfigAware,
        HBaseDataSourceAware, HBaseService, HBaseAdminService, BasicService{
}
