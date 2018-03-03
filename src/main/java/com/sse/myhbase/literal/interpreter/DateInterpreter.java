package com.sse.myhbase.literal.interpreter;

import com.sse.myhbase.literal.AbstractLiteralInterpreter;
import com.sse.myhbase.util.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: Cai Shunda
 * @description:
 * @date: Created in 19:54 2018/3/1
 * @modified by:
 */
public class DateInterpreter extends AbstractLiteralInterpreter{
    private static List<String> dateFormats = new ArrayList<>();
    static {
        dateFormats.add(DateUtil.MillSecondFormat);
        dateFormats.add(DateUtil.SecondFormat);
        dateFormats.add(DateUtil.MinuteFormat);
        dateFormats.add(DateUtil.HourFormat);
        dateFormats.add(DateUtil.DayFormat);
    }


    @Override
    protected Object interpret_internal(String literalValue) {
        for (String s : dateFormats) {
            Date date = DateUtil.parse(literalValue, s);
            if (date != null) {
                return date;
            }
        }
        return null;
    }

    @Override
    public Class getTypeCanInterpret() {
        return Date.class;
    }
}
