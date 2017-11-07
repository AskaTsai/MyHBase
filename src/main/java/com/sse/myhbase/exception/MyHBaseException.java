package com.sse.myhbase.exception;

/**
 * @Author: Cai Shunda
 * @Description: 所有的异常都需要继承这个类
 * @Date: Created in 12:02 2017/11/7
 * @Modified by:
 */
public class MyHBaseException extends RuntimeException {
    private static final long serialVersionUID = 1429186750919359517L;

    public MyHBaseException(String message) {
        super(message);
    }

    public MyHBaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public MyHBaseException(Throwable cause) {
        super(cause);
    }
}
