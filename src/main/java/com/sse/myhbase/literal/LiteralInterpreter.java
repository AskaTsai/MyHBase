package com.sse.myhbase.literal;

import com.sse.myhbase.core.NotNullable;

/**
 * @author: Cai Shunda
 * @description: 将字面上的值转化成指定类型的对象
 *                  一个解释器只能解释特定类型
 * @date: Created in 17:39 2018/3/1
 * @modified by:
 */
public interface LiteralInterpreter {

    /**
     * @Author: Cai Shunda
     * @Description: 把字面值转化成java对象
     * @Param:
     * @Date: 17:42 2018/3/1
     */
    @NotNullable
    public Object interpret(@NotNullable String literalValue);


    /**
     * @Author: Cai Shunda
     * @Description: 此解释器能够提供给转化的类型
     * @Param:
     * @Date: 17:43 2018/3/1
     */
    @NotNullable
    public Class getTypeCanInterpret();
}
