package com.sse.myhbase.util;

/**
 * @author: Cai Shunda
 * @description: 解码转化的
 * @date: Created in 17:46 2017/11/8
 * @modified by:
 */
public class EncodingUtil {
    private static String[] int2Hex = new String[]{"0", "1", "2", "3", "4",
                "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};

    /**
     * @author: Cai Shunda
     * @description: 把bytes转成16进制字符串
     * @date: 20:59 2017/11/8
     */
    public static String toHexString(byte[] bytes){
        Util.checkNull(bytes);

        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(toHexString(b));
        }
        return sb.toString();
    }

    /**
     * @author: Cai Shunda
     * @description: 把一个byte转成16进制字符串
     * @date: 21:00 2017/11/8
     */
    private static String toHexString(byte b) {
        StringBuilder sb = new StringBuilder();

        int high = (b >>> 4) & 0xF;//高四位
        sb.append(int2Hex[high]);
        int low = b & 0xF;//低四位
        sb.append(int2Hex[low]);
        return sb.toString();
    }


}
