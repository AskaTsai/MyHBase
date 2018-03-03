package com.sse.myhbase.literal.interpreter;

import com.sse.myhbase.literal.AbstractLiteralInterpreter;

/**
 * @author: Cai Shunda
 * @description:
 * @date: Created in 20:30 2018/3/1
 * @modified by:
 */
public class IntegerInterpreter extends AbstractLiteralInterpreter{
    @Override
    protected Object interpret_internal(String literalValue) {
        return Integer.parseInt(literalValue);
    }

    @Override
    public Class getTypeCanInterpret() {
        return Integer.class;
    }
}
