package com.sse.myhbase.util;

import com.sse.myhbase.core.Nullable;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Cai Shunda
 * @Description:
 * @Date: Created in 19:03 2017/11/7
 * @Modified by:
 */
public class ConfigUtil {
    /**logger*/
    private static Logger logger = Logger.getLogger(ConfigUtil.class);

    private static String splitRegex = "=";

    public ConfigUtil() {
    }

    /**
     * @Author: Cai Shunda
     * @Description: 加载配置文件
     * @Date: 19:17 2017/11/7
     */
    public static Map<String, String> loadConfigFile(@Nullable InputStream inputStream) throws IOException {
        Map<String, String> result = new HashMap<>();

        if (inputStream == null) {
            return result;
        }

        LineNumberReader lineNumberReader = null;
        try {
            lineNumberReader = new LineNumberReader(new InputStreamReader(inputStream));
            for (String line = lineNumberReader.readLine(); line != null; line = lineNumberReader.readLine()){
                String[] parts = line.split(splitRegex);
                if (parts == null || parts.length != 2) {
                    logger.warn("wrong config line. line = " + line);
                    continue;
                }
                result.put(parts[0], parts[1]);
            }
        } finally {
            if (lineNumberReader != null) {
                lineNumberReader.close();
            }
        }

        return result;
    }
}
