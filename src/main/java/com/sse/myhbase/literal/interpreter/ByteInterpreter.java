package com.sse.myhbase.literal.interpreter;

import com.sse.myhbase.literal.AbstractLiteralInterpreter;

/**
 * @author: Cai Shunda
 * @description:
 * @date: Created in 19:38 2018/3/1
 * @modified by:
 */
public class ByteInterpreter extends AbstractLiteralInterpreter{
    @Override
    protected Object interpret_internal(String literalValue) {
        return Byte.parseByte(literalValue);
    }

    @Override
    public Class getTypeCanInterpret() {
        return Byte.class;
    }
}
