package com.sse.myhbase.config;

import org.apache.log4j.Logger;
import org.springframework.core.io.Resource;

import java.util.concurrent.ConcurrentHashMap;

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

    /**
     * 从配置文件里面读到的hbase表的schema
     */
    private HBaseTableSchema hBaseTableSchema = new HBaseTableSchema();

    /**
     * 配置文件设置的hbase查询的映射表
     */
    private ConcurrentHashMap<String, HBaseQuery> hbaseQueryMap = new ConcurrentHashMap<>();

    /**
     * 类的类型信息映射
     */
    private ConcurrentHashMap<String, TypeInfo> mappingType = new ConcurrentHashMap<>();

    /**
     * @Author: Cai Shunda
     * @Description: 初始化HBaseTableConfig
     * @Date: 22:49 2017/11/10
     */
    public void init() {

    }

}
