package com.sse.myhbase.config;

import org.apache.log4j.Logger;

/**
 * @Author: Cai Shunda
 * @Description: HBase表列的schema，包含列的所有元数据
 * @Date: Created in 20:40 2017/11/7
 * @Modified by:
 */
public class HBaseColoumSchema {
    /**
     *logger
     */
    private static Logger logger = Logger.getLogger(HBaseColoumSchema.class);

    //----------------xml的配置----------------------
    @ConfigAttr
    private String family;

    @ConfigAttr
    private String qualifier;

    @ConfigAttr
    private String typeName;

    @ConfigAttr
    private String typeHandlerName;

    //------------------运行时的配置-----------------------
    private byte[] familyBytes;

    private byte[] qualifierBytes;

    private Class<?> type;

    private TypeHandler typeHandler;

}
