package com.sse.myhbase.config;

import com.sse.myhbase.client.TypeInfo;
import com.sse.myhbase.exception.MyHBaseException;
import com.sse.myhbase.hql.HBaseQuery;
import com.sse.myhbase.util.CollectionUtil;
import com.sse.myhbase.util.Util;
import com.sse.myhbase.util.XmlUtil;
import org.apache.log4j.Logger;
import org.springframework.core.io.Resource;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;
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
     * 配置文件设置的hbase查询的映射表 id->HBaseQuery
     */
    private ConcurrentHashMap<String, HBaseQuery> hbaseQueryMap = new ConcurrentHashMap<>();

    /**
     * 类的类型信息映射
     */
    private ConcurrentHashMap<Class<?>, TypeInfo> mappingTypes = new ConcurrentHashMap<>();

    /**
     * @Author: Cai Shunda
     * @Description: 初始化HBaseTableConfig
     * @Date: 22:49 2017/11/10
     */
    public void init() {
        Util.checkNull(configResource);

        try {
            List<HBaseColumnSchema> hBaseColumnSchemas = new ArrayList<>();
            HBaseTableConfigParser.parseTableSchema(configResource.getInputStream(),
                    hBaseTableSchema, hBaseColumnSchemas);
            hBaseTableSchema.init(hBaseColumnSchemas);

            List<HBaseQuery> hBaseQueries = HBaseTableConfigParser.parseHBaseQuery(
                     configResource.getInputStream());

            addHBaseQueryList(hBaseQueries);

            //可改进
            List<Node> TypeInfoNodes = XmlUtil.findTopLevelNodes(
                    configResource.getInputStream(), "MappingType");
            for(Node typeInfoNode : TypeInfoNodes){
                #@#@#TypeInfo typeInfo = TypeInfo.parseNode(typeInfoNode, hBaseTableSchema);
                addTypeInfo(typeInfo);
            }

            logger.info(this);
        } catch (Exception e) {
            logger.error("parse config error.", e);
            throw new MyHBaseException("parse config error.", e);
        }

    }

    private void addTypeInfo(TypeInfo typeInfo) {
        //需要重写toString
        logger.info("register TypeInfo\n" + typeInfo);
        mappingTypes.putIfAbsent(typeInfo.getType(), typeInfo);
    }

    /**
     * @Author: Cai Shunda
     * @Description: 把HBaseQuery转化为map缓存起来
     * @Date: 22:28 2017/11/11
     */
    public void addHBaseQueryList(List<HBaseQuery> hBaseQueries) {
        CollectionUtil.checkNull(hBaseQueries);
        for(HBaseQuery hBaseQuery : hBaseQueries){
            hbaseQueryMap.put(hBaseQuery.getId(), hBaseQuery);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(hBaseTableSchema.toString());
        return sb.toString();
    }

    public Resource getConfigResource() {
        return configResource;
    }

    public void setConfigResource(Resource configResource) {
        this.configResource = configResource;
    }
}
