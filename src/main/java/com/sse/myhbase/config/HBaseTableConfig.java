package com.sse.myhbase.config;

import org.apache.log4j.Logger;
import org.springframework.core.io.Resource;

/**
 * @Author: Cai Shunda
 * @Description:
 * @Date: Created in 20:19 2017/11/2
 * @Modified by:
 */
public class HBaseTableConfig {
    /**
     * logger
     * */
    final private static Logger logger = Logger.getLogger(HBaseDataSource.class);

    /**
     * 配置资源，也就是HBase表结构的配置文件
     */
    @ConfigAttr
    private Resource configResource;

    private HBaseTableSchema hBaseTableSchema = new HBaseTableSchema();
}
