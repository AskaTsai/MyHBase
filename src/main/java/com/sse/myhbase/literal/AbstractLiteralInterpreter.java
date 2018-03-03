package com.sse.myhbase.literal;

import com.sse.myhbase.core.NotNullable;
import com.sse.myhbase.util.Util;

/**
 * @author: Cai Shunda
 * @description:
 * @date: Created in 19:17 2018/3/1
 * @modified by:
 */
abstract public class AbstractLiteralInterpreter implements LiteralInterpreter{

    abstract protected Object interpret_internal(@NotNullable String literalValue);

    @Override
    @NotNullable
    public Object interpret(@NotNullable String literalValue) {
        Util.checkNull(literalValue);
        Object object = interpret_internal(literalValue);
        Util.checkNull(object);
        return object;
    }
}
