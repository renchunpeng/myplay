package com.cpren.utils.yw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * properties工具类
 *
 * @author rongkuan on 2018/10/23
 */
public class YWPropertiesUtils {

    private static Logger logger = LoggerFactory.getLogger(YWPropertiesUtils.class);

    /**
     * 禁止实例化
     */
    private YWPropertiesUtils() {

    }

    /**
     * 读取Properties文件
     *
     * @param fileName 文件相对路径
     * @return java.util.Properties
     * @throws
     * @author rongKuan
     * @date 2018/10/17
     */
    public static Properties readProperties(String fileName) {
        InputStreamReader inputStream = null;
        Properties props = new Properties();
        try {
            inputStream = new InputStreamReader(
                    YWPropertiesUtils.class.getClassLoader().getResourceAsStream(fileName), "UTF-8");
            props.load(inputStream);
        } catch (Exception e) {
            logger.error("读取文件 【" + fileName + "】 失败", e);
        } finally {
            closeIO(inputStream);
        }
        return props;
    }

    private static void closeIO(Closeable io) {
        if (io != null) {
            try {
                io.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
