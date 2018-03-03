package com.sse.myhbase.literal.interpreter;

import com.sse.myhbase.literal.AbstractLiteralInterpreter;

/**
 * @author: Cai Shunda
 * @description:
 * @date: Created in 20:34 2018/3/1
 * @modified by:
 */
public class LongInterpreter extends AbstractLiteralInterpreter{
    @Override
    protected Object interpret_internal(String literalValue) {
        return Integer.parseInt(literalValue);
    }

    @Override
    public Class getTypeCanInterpret() {
        return Long.class;
    }
}
