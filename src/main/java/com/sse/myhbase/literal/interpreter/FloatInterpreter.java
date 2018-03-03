package com.sse.myhbase.literal.interpreter;

import com.sse.myhbase.literal.AbstractLiteralInterpreter;

/**
 * @author: Cai Shunda
 * @description:
 * @date: Created in 20:28 2018/3/1
 * @modified by:
 */
public class FloatInterpreter extends AbstractLiteralInterpreter{
    @Override
    protected Object interpret_internal(String literalValue) {
        return Float.parseFloat(literalValue);
    }

    @Override
    public Class getTypeCanInterpret() {
        return Float.class;
    }
}
