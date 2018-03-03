package com.sse.myhbase.literal.interpreter;

import com.sse.myhbase.literal.AbstractLiteralInterpreter;

/**
 * @author: Cai Shunda
 * @description:
 * @date: Created in 20:39 2018/3/1
 * @modified by:
 */
public class StringInterpreter extends AbstractLiteralInterpreter{
    @Override
    protected Object interpret_internal(String literalValue) {
        return literalValue;
    }

    @Override
    public Class getTypeCanInterpret() {
        return String.class;
    }
}
