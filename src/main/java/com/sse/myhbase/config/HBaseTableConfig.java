package com.sse.myhbase.config;

import com.sse.myhbase.client.HBaseTable;
import com.sse.myhbase.client.TypeInfo;
import com.sse.myhbase.core.NotNullable;
import com.sse.myhbase.core.Nullable;
import com.sse.myhbase.exception.MyHBaseException;
import com.sse.myhbase.hql.HBaseQuery;
import com.sse.myhbase.util.CollectionUtil;
import com.sse.myhbase.util.StringUtil;
import com.sse.myhbase.util.Util;
import com.sse.myhbase.util.XmlUtil;
import org.apache.log4j.Logger;
import org.springframework.core.io.Resource;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: Cai Shunda
 * @description:
 * @date: Created in 20:19 2017/11/2
 * @modified by:
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
     * @author: Cai Shunda
     * @description: 初始化HBaseTableConfig
     * @date: 22:49 2017/11/10
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
                TypeInfo typeInfo = TypeInfo.parseNode(typeInfoNode, hBaseTableSchema);
                addTypeInfo(typeInfo);
            }

            logger.info(this);
        } catch (Exception e) {
            logger.error("parse config error.", e);
            throw new MyHBaseException("parse config error.", e);
        }

    }

    /**
     * @author: Cai Shunda
     * @description:  从映射信息中获取对应POJO的类型信息
     *          会通过三种方式来获取映射信息：
     *          1. 通过配置的xml文件
     *          2. 通过@HBaseTable注解发现映射关系，同时会记录下来
     *          3. 通过反射来发现映射关系，同时会记录下来
     * @Param:
     * @date: 21:36 2017/11/19
     */
    @Nullable
    public TypeInfo findTypeInfo(@NotNullable Class<?> type) {
        Util.checkNull(type);

        //xml文件中
        TypeInfo result = mappingTypes.get(type);
        if (result != null) {
            return result;
        }

        //@HBaseTable注解
        HBaseTable annotation = type.getAnnotation(HBaseTable.class);
        if (annotation != null) {
            //把type对应的类解析出来，放到映射变量中
            result = TypeInfo.parse(type);
            //记录
            addTypeInfo(result);
            return result;
        }

        //用反射的方式去找是否有映射关系
        result = TypeInfo.parseInAir(type, hBaseTableSchema);
        if (result != null) {
            //存在对应映射关系，记录下来
            addTypeInfo(result);
            return result;
        }

        throw new MyHBaseException("can not find type info for type = " + type);
    }

    private void addTypeInfo(TypeInfo typeInfo) {
        logger.info("register TypeInfo\n" + typeInfo);
        mappingTypes.putIfAbsent(typeInfo.getType(), typeInfo);
    }

    /**
     * @author: Cai Shunda
     * @description: 把HBaseQuery转化为map缓存起来
     * @date: 22:28 2017/11/11
     */
    public void addHBaseQueryList(List<HBaseQuery> hBaseQueries) {
        CollectionUtil.checkNull(hBaseQueries);
        for(HBaseQuery hBaseQuery : hBaseQueries){
            hbaseQueryMap.put(hBaseQuery.getId(), hBaseQuery);
        }
    }

    public boolean isAutoCreate() {
        return hBaseTableSchema.isAutoCreate();
    }

    public List<String> getAllConfigedFamilys() {
        Set<String> familySet = hBaseTableSchema.getAllConfigedFamilys();
        List<String> result = new ArrayList<>();
        result.addAll(familySet);
        return result;
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

    public HBaseTableSchema gethBaseTableSchema() {
        return hBaseTableSchema;
    }
}
