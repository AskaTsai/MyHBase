package com.sse.myhbase.literal.interpreter;

import com.sse.myhbase.literal.AbstractLiteralInterpreter;

/**
 * @author: Cai Shunda
 * @description:
 * @date: Created in 20:24 2018/3/1
 * @modified by:
 */
public class DoubleInterpreter extends AbstractLiteralInterpreter{
    @Override
    protected Object interpret_internal(String literalValue) {
        return Double.parseDouble(literalValue);
    }

    @Override
    public Class getTypeCanInterpret() {
        return Double.class;
    }
}
