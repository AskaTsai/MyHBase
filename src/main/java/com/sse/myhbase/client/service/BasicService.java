package com.sse.myhbase.client.service;

import com.sse.myhbase.client.MyHBaseDOWithKeyResult;
import com.sse.myhbase.client.PutRequest;
import com.sse.myhbase.client.QueryExtInfo;
import com.sse.myhbase.client.RowKey;

import java.util.List;

/**
 * @Author: Cai Shunda
 * @Description: 提供基础服务
 * @Date: Created in 16:45 2017/11/19
 * @Modified by:
 */
public interface BasicService {
    /**
     * @Author: Cai Shunda
     * @Description: put POJO（java简单对象）
     * @Param: rowKey 行键
     * @Param: t 放入的java对象
     * @Date: 16:57 2017/11/19
     */
    public <T> void putObject(RowKey rowKey, T t);

    /**
     * @Author: Cai Shunda
     * @Description: put java简单对象的List
     * @Param: putRequestList POJO List
     * @Date: 20:45 2017/11/19
     */
    public <T> void putObjectList(List<PutRequest<T>> putRequestList);

    /**
     * @Author: Cai Shunda
     * @Description: 根据行键rowkey获取对象
     * @Param:
     * @Date: 22:57 2017/11/21
     */
    public <T> T findObject(RowKey rowKey, Class<? extends T> type);

    /**
     * @Author: Cai Shunda
     * @Description: 通过RowKey找到对象和RowKey行键
     * @Param:
     * @Date: 21:08 2017/11/23
     */
    public <T> MyHBaseDOWithKeyResult<T> findObjectAndKey(RowKey rowKey, Class<? extends T> type);

    /**
     * @Author: Cai Shunda
     * @Description: 通过RowKey找到对象和RowKey行键
     * @Param:
     * @Date: 21:47 2017/11/23
     */
    public <T> MyHBaseDOWithKeyResult<T> findObjectAndKey(RowKey rowKey, Class<? extends T> type, QueryExtInfo queryExtInfo);


}
