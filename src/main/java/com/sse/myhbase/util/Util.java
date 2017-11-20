package com.sse.myhbase.util;

import com.sse.myhbase.client.PutRequest;
import com.sse.myhbase.client.RowKey;
import com.sse.myhbase.core.Nullable;
import com.sse.myhbase.exception.MyHBaseException;

import java.io.Closeable;

/**
 * @Author: Cai Shunda
 * @Description: 客户端Util
 * @Date: Created in 11:45 2017/11/7
 * @Modified by:
 */
public class Util {
    /**
     * @Author: Cai Shunda
     * @Description: 检查bool类型是不是false
     * @Date: 11:58 2017/11/7
     */
    public static void check(boolean bool){
        if (bool == false) {
            throw new MyHBaseException("bool is false.");
        }
    }

    /**
     * @Author: Cai Shunda
     * @Description: 检查String不为null或不为空
     * @Date: 12:30 2017/11/7
     */
    public static void checkEmptyString(String str) {
        if (StringUtil.isEmptyString(str)) {
            throw new MyHBaseException("str is null or empty.");
        }
    }

    /**
     * @Author: Cai Shunda
     * @Description: 检查确保对象不为null
     * @Date: 15:41 2017/11/7
     */
    public static void checkNull(Object object) {
        if (object == null) {
            throw new MyHBaseException("obj is null.");
        }
    }

    /**
     * @Author: Cai Shunda
     * @Description:检查是否为正数
     * @Date: 16:40 2017/11/7
     */
    public static void checkPositive(int num) {
        if (!NumberUtil.isPositive(num)) {
            throw new MyHBaseException("num is negative.");
        }
    }

    /**
     * @Author: Cai Shunda
     * @Description: 检查字节数组values是否是指定长度
     * @Date: 20:41 2017/11/10
     */
    public static void checkLength(byte[] values, int length) {
        Util.checkNull(values);

        if (values.length != length) {
            throw new MyHBaseException("checkLength error. values.length="
                    + values.length + " length=" + length);
        }
    }
    /**
     * @Author: Cai Shunda
     * @Description: 检查PutRequest
     * @Param:
     * @Date: 21:19 2017/11/19
     */
    public static void checkPutRequest(PutRequest<?> putRequest) {
        checkNull(putRequest);
        checkNull(putRequest.getT());
        checkRowKey(putRequest.getRowKey());
    }

    /**
     * @Author: Cai Shunda
     * @Description: 检查行键RowKey
     * @Param:
     * @Date: 21:19 2017/11/19
     */
    private static void checkRowKey(RowKey rowKey) {
        checkNull(rowKey);
        if (rowKey.toBytes() == null) {
            throw new MyHBaseException("rowkey bytes is null, rowkey=" + rowKey);
        }
    }

    /**
     * @Author: Cai Shunda
     * @Description: 关闭连接
     * @Param:
     * @Date: 22:15 2017/11/20
     */
    public static void close(@Nullable Closeable closeable) {
        if (closeable == null) {
            return;
        }
        try {
            closeable.close();
        } catch (Exception e) {
            throw new MyHBaseException("close closeable exception." + e);
        }
    }
}
