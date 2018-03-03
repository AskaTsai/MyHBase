package com.sse.myhbase.config;

import com.sse.myhbase.literal.LiteralInterpreter;
import com.sse.myhbase.literal.interpreter.*;
import com.sse.myhbase.util.ClassUtil;
import com.sse.myhbase.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : Cai Shunda
 * @description: HBase运行时的一些配置
 * @date: Created in 21:19 2017/11/28
 * @modified by:
 */
public class MyHBaseRuntimeSetting {

    /**
     * 多少Result，可以理解为多少个Result进行一次RPC操作(竖向，面向行的优化处理)
     */
    private int scanCachingSize = 20;

    /**
     * 删除Batch
     */
    private int deleteBatchSize = 50;

    /**
     * Scan操作的CacheSize是否可以由用户设置
     */
    private boolean intelligentScanSize;


    /**
     * 文本解释器
     */
    private Map<Class, LiteralInterpreter> literalInterpreterCache = new HashMap<>();
    private Map<Class, LiteralInterpreter> buildInLiteralInterpreterCache = new HashMap<>();


    public MyHBaseRuntimeSetting() {
        List<LiteralInterpreter> buildInLiteralInterpreterList = new ArrayList<LiteralInterpreter>();
        buildInLiteralInterpreterList.add(new BooleanInterpreter());
        buildInLiteralInterpreterList.add(new ByteInterpreter());
        buildInLiteralInterpreterList.add(new CharInterpreter());
        buildInLiteralInterpreterList.add(new DateInterpreter());
        buildInLiteralInterpreterList.add(new DoubleInterpreter());
        buildInLiteralInterpreterList.add(new FloatInterpreter());
        buildInLiteralInterpreterList.add(new IntegerInterpreter());
        buildInLiteralInterpreterList.add(new LongInterpreter());
        buildInLiteralInterpreterList.add(new ShortInterpreter());
        buildInLiteralInterpreterList.add(new StringInterpreter());
        buildInLiteralInterpreterList.add(new HexBytesInterpreter());
        for (LiteralInterpreter interpreter : buildInLiteralInterpreterList) {
            Class type = ClassUtil.tryConvertToBoxClass(interpreter.getTypeCanInterpret());
            buildInLiteralInterpreterCache.put(type, interpreter);
        }

    }

    public Object interpret(Class type, String literalValue) {
        Util.checkNull(type);
        Util.checkNull(literalValue);

        Class temtype = ClassUtil.tryConvertToBoxClass(type);

        if (literalInterpreterCache.containsKey(temtype)) {
            return literalInterpreterCache.get(temtype).interpret(literalValue);
        }

        if (buildInLiteralInterpreterCache.containsKey(temtype)) {
            return buildInLiteralInterpreterCache.get(temtype).interpret(literalValue);
        }

        Object result = null;
        if (temtype.isEnum()) {
            result = Enum.valueOf(type, literalValue);
        }
        Util.checkNull(result);
        return result;

    }

    public int getScanCachingSize() {
        return scanCachingSize;
    }

    public void setScanCachingSize(int scanCachingSize) {
        this.scanCachingSize = scanCachingSize;
    }

    public int getDeleteBatchSize() {
        return deleteBatchSize;
    }

    public void setDeleteBatchSize(int deleteBatchSize) {
        this.deleteBatchSize = deleteBatchSize;
    }

    public boolean isIntelligentScanSize() {
        return intelligentScanSize;
    }

    public void setIntelligentScanSize(boolean intelligentScanSize) {
        this.intelligentScanSize = intelligentScanSize;
    }
}
