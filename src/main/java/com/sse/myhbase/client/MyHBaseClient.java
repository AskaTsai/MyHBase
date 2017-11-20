package com.sse.myhbase.client;

import com.sse.myhbase.client.service.*;

/**
 * @Author: Cai Shunda
 * @Description: 整个框架的主要入口点
 * @Date: Created in 21:51 2017/11/1
 * @Modified by:
 */
public interface MyHBaseClient extends HBaseTableConfigAware,
        HBaseDataSourceAware, HBaseService, HBaseAdminService, BasicService{
}
