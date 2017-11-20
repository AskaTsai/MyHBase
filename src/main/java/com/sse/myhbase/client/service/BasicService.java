package com.sse.myhbase.client.service;

import com.sse.myhbase.client.PutRequest;
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
}
