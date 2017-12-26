package com.sse.myhbase.client.service;

import com.sse.myhbase.client.MyHBaseDOWithKeyResult;
import com.sse.myhbase.client.PutRequest;
import com.sse.myhbase.client.QueryExtInfo;
import com.sse.myhbase.client.RowKey;

import java.util.List;

/**
 * @author: Cai Shunda
 * @description: 提供基础服务
 * @date: Created in 16:45 2017/11/19
 * @modified by:
 */
public interface BasicService {
    /**
     * @author: Cai Shunda
     * @description: put POJO（java简单对象）
     * @Param: rowKey 行键
     * @Param: t 放入的java对象
     * @date: 16:57 2017/11/19
     */
    public <T> void putObject(RowKey rowKey, T t);

    /**
     * @author: Cai Shunda
     * @description: put java简单对象的List
     * @Param: putRequestList POJO List
     * @date: 20:45 2017/11/19
     */
    public <T> void putObjectList(List<PutRequest<T>> putRequestList);

    /**
     * @author: Cai Shunda
     * @description: 根据行键rowkey获取对象
     * @Param:
     * @date: 22:57 2017/11/21
     */
    public <T> T findObject(RowKey rowKey, Class<? extends T> type);

    /**
     * @author: Cai Shunda
     * @description: 通过RowKey找到对象和RowKey行键
     * @Param:
     * @date: 21:08 2017/11/23
     */
    public <T> MyHBaseDOWithKeyResult<T> findObjectAndKey(RowKey rowKey, Class<? extends T> type);

    /**
     * @author: Cai Shunda
     * @description: 通过RowKey找到对象和RowKey行键
     * @Param:
     * @date: 21:47 2017/11/23
     */
    public <T> MyHBaseDOWithKeyResult<T> findObjectAndKey(RowKey rowKey, Class<? extends T> type, QueryExtInfo queryExtInfo);

    /**
     * @author: Cai Shunda
     * @description: 找到在【startRowKey, endRowKey）之间的所有type类型的java对象
     * @Param:
     * @date: 20:30 2017/11/27
     */
    public <T> List<T> findObjectList(RowKey startRowKey, RowKey endRowKey, Class<? extends T> type);

    /**
     * @author: Cai Shunda
     * @description: 找到在【startRowKey, endRowKey）之间的所有type类型的java对象
     * @Param:
     * @date: 20:40 2017/11/27
     */
    public <T> List<MyHBaseDOWithKeyResult<T>> findObjectAndKeyList(RowKey startRowKey, RowKey endRowKey, Class<? extends T> type);

    /**
     * @author: Cai Shunda
     * @description: 找到在【startRowKey, endRowKey）之间的所有type类型的java对象
     * @Param:
     * @date: 20:40 2017/11/27
     */
    public <T> List<MyHBaseDOWithKeyResult<T>> findObjectAndKeyList(RowKey startRowKey, RowKey endRowKey,
                                                                    Class<? extends T> type, QueryExtInfo queryExtInfo);

    /**
     * @Author: Cai Shunda
     * @Description: 删除指定行键的HBase中的对象
     * @Param:
     * @Date: 20:28 2017/11/29
     */
    public void deleteObject(RowKey rowKey, Class<?> type);

    /**
     * @Author: Cai Shunda
     * @Description: 删除所有指定行键的HBase的对象
     * @Param:
     * @Date: 21:23 2017/11/29
     */
    public void deleteObjectList(List<RowKey> rowKeyList, Class<?> type);


    /**
     * @Author: Cai Shunda
     * @Description: 删除指定范围行键【startRowKey, endRowKey）的HBase对象
     * @Param:
     * @Date: 16:19 2017/12/25
     */
    public void deleteObjectWithRange(RowKey startRowKey, RowKey endRowKey, Class<?> type);

}
