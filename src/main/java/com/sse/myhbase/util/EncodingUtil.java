package com.sse.myhbase.util;

import com.sse.myhbase.exception.MyHBaseException;

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
     * @description: 把一个byte转成16进制字符串,相当于把前四位二进制和后四位二进制分别转成十六进制字符
     * @date: 21:00 2017/11/8
     */
    private static String toHexString(byte b) {
        StringBuilder sb = new StringBuilder();

        int high = (b >>> 4) & 0xF;//高四位,0xF=1111
        sb.append(int2Hex[high]);
        int low = b & 0xF;//低四位
        sb.append(int2Hex[low]);
        return sb.toString();
    }
    /**
     * @Author: Cai Shunda
     * @Description: 把十六进制字符串转化查byte数组
     * @Param:
     * @Date: 12:35 2018/3/2
     */
    public static byte[] parseBytesFromHexString(String hexStr) {
        String fixedHexStr = fixHexString(hexStr);
        
        if (fixedHexStr.length() %2 !=0) {
            throw new MyHBaseException("error hexStrLengtg. hexStr=" + hexStr);
        }
        
        byte[] result = new byte[fixedHexStr.length()/2];

        for (int i = 0; i < result.length; i++) {
            String item = fixedHexStr.substring(i * 2, i * 2 + 2);
            int value = Integer.parseInt(item, 16);
            result[i] = (byte) value;
        }
        return result;
    }

    /**
     * @Author: Cai Shunda
     * @Description: 去除0-9，A-F，a-f以外的其他字符
     * @Param:
     * @Date: 12:39 2018/3/2
     */
    public static String fixHexString(String hexStr) {
        Util.checkNull(hexStr);

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < hexStr.length(); i++) {
            char c = hexStr.charAt(i);
            if (c >= '0' && c <= '9') {
                sb.append(c);
                continue;
            }
            if (c >= 'A' && c <= 'F') {
                sb.append(c);
                continue;
            }
            if (c >= 'a' && c <= 'f') {
                sb.append(c);
                continue;
            }
        }

        return sb.toString();

    }

}
