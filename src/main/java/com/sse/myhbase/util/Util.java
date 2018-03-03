package com.sse.myhbase.util;

import com.sse.myhbase.client.DeleteRequest;
import com.sse.myhbase.client.PutRequest;
import com.sse.myhbase.client.RowKey;
import com.sse.myhbase.core.Nullable;
import com.sse.myhbase.exception.MyHBaseException;

import java.io.Closeable;

/**
 * @author: Cai Shunda
 * @description: 客户端Util
 * @date: Created in 11:45 2017/11/7
 * @modified by:
 */
public class Util {
    /**
     * @author: Cai Shunda
     * @description: 检查bool类型是不是false
     * @date: 11:58 2017/11/7
     */
    public static void check(boolean bool){
        if (bool == false) {
            throw new MyHBaseException("bool is false.");
        }
    }

    /**
     * @author: Cai Shunda
     * @description: 检查String不为null或不为空
     * @date: 12:30 2017/11/7
     */
    public static void checkEmptyString(String str) {
        if (StringUtil.isEmptyString(str)) {
            throw new MyHBaseException("str is null or empty.");
        }
    }

    /**
     * @author: Cai Shunda
     * @description: 检查确保对象不为null
     * @date: 15:41 2017/11/7
     */
    public static void checkNull(Object object) {
        if (object == null) {
            throw new MyHBaseException("obj is null.");
        }
    }

    /**
     * @author: Cai Shunda
     * @description:检查是否为正数
     * @date: 16:40 2017/11/7
     */
    public static void checkPositive(int num) {
        if (!NumberUtil.isPositive(num)) {
            throw new MyHBaseException("num is negative.");
        }
    }

    /**
     * @author: Cai Shunda
     * @description: 检查字节数组values是否是指定长度
     * @date: 20:41 2017/11/10
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
     * @Description:  检查字符串是不是指定长度
     * @Param:
     * @Date: 19:46 2018/3/1
     */
    public static void checkLength(String str, int length) {
        Util.checkNull(str);

        if (str.length() != length) {
            throw new MyHBaseException("checkLength error. str=" + str + " length=" + length);
        }
    }

    /**
     * @author: Cai Shunda
     * @description: 检查PutRequest
     * @Param:
     * @date: 21:19 2017/11/19
     */
    public static void checkPutRequest(PutRequest<?> putRequest) {
        checkNull(putRequest);
        checkNull(putRequest.getT());
        checkRowKey(putRequest.getRowKey());
    }

    public static void checkDeleteRequest(DeleteRequest deleteRequest) {
        checkNull(deleteRequest);
        checkRowKey(deleteRequest.getRowKey());
    }

    /**
     * @author: Cai Shunda
     * @description: 检查行键RowKey
     * @Param:
     * @date: 21:19 2017/11/19
     */
    public static void checkRowKey(RowKey rowKey) {
        checkNull(rowKey);
        if (rowKey.toBytes() == null) {
            throw new MyHBaseException("rowkey bytes is null, rowkey=" + rowKey);
        }
    }

    /**
     * @author: Cai Shunda
     * @description: 关闭连接
     * @Param:
     * @date: 22:15 2017/11/20
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
