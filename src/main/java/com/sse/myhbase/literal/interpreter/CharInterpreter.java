package com.sse.myhbase.literal.interpreter;

import com.sse.myhbase.literal.AbstractLiteralInterpreter;
import com.sse.myhbase.util.Util;

/**
 * @author: Cai Shunda
 * @description:
 * @date: Created in 19:44 2018/3/1
 * @modified by:
 */
public class CharInterpreter extends AbstractLiteralInterpreter{
    @Override
    protected Object interpret_internal(String literalValue) {
        Util.checkLength(literalValue, 1);;
        return literalValue.charAt(0);
    }

    @Override
    public Class getTypeCanInterpret() {
        return Character.class;
    }
}
