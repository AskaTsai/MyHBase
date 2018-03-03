package com.sse.myhbase.literal.interpreter;

import com.sse.myhbase.literal.AbstractLiteralInterpreter;

/**
 * @author: Cai Shunda
 * @description:
 * @date: Created in 19:16 2018/3/1
 * @modified by:
 */
public class BooleanInterpreter extends AbstractLiteralInterpreter {
    @Override
    protected Object interpret_internal(String literalValue) {
        return Boolean.parseBoolean(literalValue);
    }

    @Override
    public Class getTypeCanInterpret() {
        return Boolean.class;
    }
}
