package com.sse.myhbase.literal.interpreter;

import com.sse.myhbase.literal.AbstractLiteralInterpreter;
import com.sse.myhbase.type.ext.HexBytes;

/**
 * @author: Cai Shunda
 * @description:
 * @date: Created in 20:40 2018/3/1
 * @modified by:
 */
public class HexBytesInterpreter extends AbstractLiteralInterpreter{
    @Override
    protected Object interpret_internal(String literalValue) {
        return new HexBytes(literalValue);
    }

    @Override
    public Class getTypeCanInterpret() {
        return HexBytes.class;
    }
}
