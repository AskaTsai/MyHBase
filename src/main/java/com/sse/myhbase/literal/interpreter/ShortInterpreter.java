package com.sse.myhbase.literal.interpreter;

import com.sse.myhbase.literal.AbstractLiteralInterpreter;

/**
 * @author: Cai Shunda
 * @description:
 * @date: Created in 20:35 2018/3/1
 * @modified by:
 */
public class ShortInterpreter extends AbstractLiteralInterpreter{
    @Override
    protected Object interpret_internal(String literalValue) {
        return Short.parseShort(literalValue);
    }

    @Override
    public Class getTypeCanInterpret() {
        return Short.class;
    }
}
