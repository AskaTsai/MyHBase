package com.sse.myhbase.config;

import com.sse.myhbase.core.Nullable;
import com.sse.myhbase.exception.MyHBaseException;
import com.sse.myhbase.hbase.HTablePoolAdaptor;
import com.sse.myhbase.hbase.HTablePoolService;
import com.sse.myhbase.hbase.MyHBaseHTablePool;
import com.sse.myhbase.util.ConfigUtil;
import com.sse.myhbase.util.StringUtil;
import com.sse.myhbase.util.Util;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.*;
import org.apache.log4j.Logger;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: Cai Shunda
 * @Description: 代表HBase的一个数据源
 * @Date: Created in 20:19 2017/11/2
 * @Modified by:
 */
public class HBaseDataSource {
    /**
     * logger
     * */
    final private static Logger logger = Logger.getLogger(HBaseDataSource.class);
    //--------------------配置的配置项-------------------------
    /**
     * 数据源id
     * */
    @ConfigAttr
    private String id;

    /**
     * 连接hbase数据源所需的配置资源，例如zookeep的配置
     * */
    @ConfigAttr
    private List<Resource> configResources;

    /**
     * @see com.sse.myhbase.config.HBaseDataSource.PoolType
     * hbase表连接池的实现类型
     * "MyHBaseHTablePool" -> MyHBaseHTablePool
     * "DefaultHTablePool" -> DefaultHTablePool
     * DefaultHTablePool.
     * */
    @Nullable
    @ConfigAttr
    private String hTablePoolType;

    /**
     * 连接池的最大连接数，这里默认是10
     * */
    @ConfigAttr
    private int poolMaxSize = 10;

    /**
     * 表连接工厂实例
     * */
    @Nullable
    @ConfigAttr
    private HTableInterfaceFactory hTablaeInterfaceFactory;

    /**
     * 配合连接池类型为MyHbaseHTablePool时候使用
     * */
    @ConfigAttr
    private long flushInterval    = -1L;

    //--------------------运行时的配置-------------------------
    /**
     * hbase的最终配置项
     * */
    private Map<String, String> finalHBaseConfig = new HashMap<String, String>();

    /**
     * hbase配置
     * */
    private Configuration hbaseConfiguration;

    /**
     * 连接池类型
     * */
    private PoolType poolType = PoolType.DefaultHTablePool;

    /**
     * 用户可以提供自定义的表连接池服务
     * */
    private HTablePoolService hTablePoolService;

    /**
     * HBase的连接
     * */
    private Connection connection;

    /**
     * 初始化数据源
     * */
    public void init() {
        try {
            System.setProperty("javax.xml.parsers.DocumentBuilderFactory",
                    "com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl");
            System.setProperty("javax.xml.parsers.SAXParserFactory",
                    "com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl");

            if (PoolType.MyHBaseHTablePool.name().equals(hTablePoolType)) {
                poolType = PoolType.MyHBaseHTablePool;
            } else {
                poolType = PoolType.DefaultHTablePool;
            }
            initHBaseConfiguration();
            initConnection();
            initHTablePoolService();
            logger.info(this);//
        } catch (Exception e) {
            logger.error("HBaseDataSource init error.", e);
            throw new MyHBaseException("HBaseDataSource init error.", e);
        }

    }

    /**
     * 通过表名获取特定的表连接
     * */
    public HTable getHTable(String tableName) {
        Util.checkEmptyString(tableName);
        return hTablePoolService.getTable(tableName);
    }

    /**
     *  获取一个HBaseAdmin
     * */
    public HBaseAdmin getHBaseAdmin() {
        try {
            Util.checkNull(connection);
            return (HBaseAdmin) connection.getAdmin();
        } catch (Exception e) {
            logger.error("can not get HBaseAdmin.", e);
            throw new MyHBaseException("can not get HBaseAdmin.", e);
        }

    }

    /**
     *  初始化HBaseConfiguration
     * */
    private void initHBaseConfiguration() {
        try {
            if (configResources != null) {
                for (Resource resource : configResources) {
                    finalHBaseConfig.putAll(ConfigUtil.loadConfigFile(resource.getInputStream()));
                }
            }

            hbaseConfiguration = HBaseConfiguration.create();
            for (Map.Entry<String, String> entry :finalHBaseConfig.entrySet()) {
                hbaseConfiguration.set(entry.getKey(), entry.getValue());
            }
        } catch (Exception e) {
            logger.error("parse config file error.", e);
            throw new MyHBaseException("parse config file error.", e);
        }
    }

    /**
     * @Author: Cai Shunda
     * @Description: 初始化HBase连接
     * @Date: 17:22 2017/11/7
     */
    private void initConnection() {
        try {
            ExecutorService executor = Executors.newFixedThreadPool(poolMaxSize);
            connection = ConnectionFactory.createConnection(hbaseConfiguration, executor);
        } catch (IOException e) {
            logger.error("initConnection error.", e);
            throw new MyHBaseException("initConnection error.", e);
        }
    }

    /**
     * @Author: Cai Shunda
     * @Description: 初始化HBase的表连接池服务
     * @Date: 15:10 2017/11/7
     */
    private void initHTablePoolService() {
        if (hTablePoolService != null) {
            //表示用户已经提供了连接服务了,不需要再自动初始化了
            return;
        }
        try {

            switch (poolType) {
                case DefaultHTablePool:
                    hTablePoolService = new HTablePoolAdaptor(connection);
                    break;
                case MyHBaseHTablePool:
                    hTablePoolService = new MyHBaseHTablePool(connection, flushInterval);
                    break;
                default:
                    throw new MyHBaseException("unknow pooltype, can not init pool.");
            }
        } catch (Exception e) {
            logger.error("initHTablePoolService error.", e);
            throw new MyHBaseException("initHTablePoolService error.", e);
        }

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n==============HBaseDataSource=================\n");
        StringUtil.appendKeyValue(sb, "ID" , "id" , id);
        StringUtil.appendMap(sb, "FINAL HBASE CONFIGURATION", finalHBaseConfig);
        sb.append("===============HBaseDataSource=================\n");
        return sb.toString();
    }

    public List<Resource> getConfigResources() {
        return configResources;
    }

    public void setConfigResources(List<Resource> configResources) {
        this.configResources = configResources;
    }

    /**
     * PoolType.
     * */
    private static enum PoolType {
        DefaultHTablePool, MyHBaseHTablePool;
    }
}
