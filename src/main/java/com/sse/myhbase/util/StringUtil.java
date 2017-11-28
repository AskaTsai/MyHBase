package com.sse.myhbase.util;

import com.sse.myhbase.core.Nullable;

import java.util.Map;

/**
 * @author: Cai Shunda
 * @description: 字符相关
 * @date: Created in 12:19 2017/11/7
 * @modified by:
 */
public class StringUtil {
    /**
     * @author: Cai Shunda
     * @description: 检查字符串是否是null或者""
     * @date: 12:21 2017/11/7
     */
    public static boolean isEmptyString(String str) {
        return str == null ||str.isEmpty();
    }

    /**
     * @author: Cai Shunda
     * @description: 检查字符串不为null且不为""
     * @date: 11:12 2017/11/8
     */
    public static boolean isNotEmptyString(String str) {return !isEmptyString(str);}

    /**
     * @author: Cai Shunda
     * @description: 往字符串追加key-value，形式是$key=$value
     * @date: 22:28 2017/11/13
     */
    public static void appendKeyValue(StringBuilder sb, @Nullable String msg, String key, Object value)  {
        Util.checkNull(sb);
        if (isNotEmptyString(msg)) {
            sb.append("#" +msg + "#\n");
        }
        sb.append(key + "=" + value + "\n");
    }

    /**
     * @author: Cai Shunda
     * @description: 往字符串里面追加Map的key-value，形式是$key=$value
     * @date: 22:37 2017/11/13
     */
    public static void appendMap(StringBuilder sb, @Nullable String msg, Map<String,String> map) {
        Util.checkNull(sb);
        if (isNotEmptyString(msg)) {
            sb.append("#" +msg + "#\n");
        }
        if (map != null) {
            for (String key : map.keySet()) {
                sb.append(key + "=" + map.get(key) + "\n");
            }
        }
    }
}
