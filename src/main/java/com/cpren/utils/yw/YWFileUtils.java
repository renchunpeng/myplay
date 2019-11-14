package com.cpren.utils.yw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class YWFileUtils {

    private static Logger logger = LoggerFactory.getLogger(YWFileUtils.class);

    /**
     * 禁止实例化
     */
    private YWFileUtils() {

    }

    /**
     * @param filename 文件名
     * @return
     * @describe 获取文件扩展名
     * @author y.you
     * @date 2018年10月15日 20:46
     * @version V5.0
     */
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }

    /**
     * @param path
     * @param delimiter
     * @describe 判断文件夹是否存在，不存在则创建
     * @author y.you
     * @date 2018年10月15日 20:46
     * @version V5.0
     */
    public static void isFolderExist(String path, String delimiter) {
        File dirPath = null;
        try {
            dirPath = new File(path);
            if (dirPath.exists() || dirPath.isDirectory()) {
                return;
            }
            String[] paths = YWStringUtils.spliceString(path, delimiter);
            String dir = paths[0];
            for (int i = 0; i < paths.length - 1; i++) {
                dir = dir + "/" + paths[i + 1];
                dirPath = new File(dir);
                if (!dirPath.exists() && !dirPath.isDirectory()) {
                    dirPath.mkdir();
                }
            }
        } catch (Exception err) {
            logger.error("文件夹创建发生异常!", err);
        }
    }

    /**
     * @param filePath 文件在磁盘上的绝对路径
     * @return 文件删除成功返回true，否则返回false
     * @describe 文件删除
     * @author y.you
     * @date 2018年10月17日 11:57
     * @version V5.0
     */
    public static boolean deleteFile(String... filePath) {
        Boolean flag = false;
        if (filePath == null || filePath.length == 0) {
            return flag;
        }
        File file = null;
        try {
            for (String path : filePath) {
                if (YWStringUtils.isBlank(path)) {
                    continue;
                }
                file = new File(path);
                // 路径为文件且不为空则进行删除
                if (file.isFile() && file.exists()) {
                    file.delete();
                }
            }
            flag = true;
        } catch (Exception e) {
            logger.error("删除文件异常！ 异常为：{}", e.getMessage());
        }
        return flag;
    }

    /**
     * @param agreement 域名协议http/https
     * @param domain    域名地址
     * @param path      文件相对路径（/upload开头的路径）
     * @return
     * @describe 组装素材http访问路径
     * @author y.you
     * @date 2018年10月19日 13:38
     * @version V5.0
     */
    public static String getMaaterialUrl(String agreement, String domain, String path) {
        if (YWStringUtils.isBlank(agreement) || YWStringUtils.isBlank(domain) || YWStringUtils.isBlank(path)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(agreement).append("://").append(domain);
        if (path.startsWith("/")) {
            sb.append(path);
        } else {
            sb.append("/").append(path);
        }
        return sb.toString();
    }

    /**
     * @param path 文件路径
     * @return
     * @describe 根据文件路径读取文本文件内容
     * @author y.you
     * @date 2018年10月23日 14:49
     * @version V5.0
     */
    public static String getTextFile(String path) {
        File file = new File(path);
        if (file == null || !file.exists()) {
            logger.warn("路径为：{}，不存在该文件！", path);
            return null;
        }
        return getTextFile(file, "UTF-8");
    }

    /**
     * 读取指定文件的文本内容
     *
     * @param file    文件
     * @param charset 文件编码格式
     * @return
     */
    public static String getTextFile(File file, String charset) {
        if (file == null) {
            logger.warn("文件不存在！");
            return null;
        }
        InputStream in = null;
        try {
            in = new FileInputStream(file);
        } catch (FileNotFoundException e) {
        }
        return getTextInputStream(in, charset);
    }

    /**
     * 读取文本文件内容
     *
     * @param inputStream    输入流
     * @param charset 文件编码格式
     * @return
     */
    public static String getTextInputStream(InputStream inputStream, String charset) {
        if (inputStream == null) {
            logger.warn("读取文本文件内容错误，输入流为null");
            return null;
        }
        StringBuilder result = new StringBuilder();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(inputStream, charset));
            String txt = br.readLine();
            while (txt != null) {
                result.append(txt);
                txt = br.readLine();
            }
            br.close();
        } catch (Exception e) {
            logger.error("文件内容读取异常：{}", e.getMessage());
            return null;
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result.toString();
    }

    /**
     * 读取文本文件内容(每行超出指定字节自动换行)
     *
     * @param inputStream    输入流
     * @param charset 文件编码格式
     * @param length 指定字节
     * @return
     */
    public static String getTextInputStream(InputStream inputStream, String charset, int length) {
        if (inputStream == null) {
            logger.warn("读取文本文件内容错误，输入流为null");
            return null;
        }
        StringBuilder result = new StringBuilder();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(inputStream, charset));
            String txt = br.readLine();
            String temp = "";
            while (txt != null) {
                while (txt.length() > length) {
                    temp = txt.substring(0, length);
                    txt = txt.substring(length);
                    result.append(temp + "\n");
                }
                result.append(txt);
                txt = br.readLine();

            }
            br.close();
        } catch (Exception e) {
            logger.error("文件内容读取异常：{}", e.getMessage());
            return null;
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result.toString();
    }

    /**
     * 删除文件夹
     *
     * @param dirfile 文件夹目录
     * @return
     */
    public static boolean deletDir(File dirfile) {
        if (!dirfile.exists()) {
            return false;
        }
        if (dirfile.isDirectory()) {
            File[] fileList = dirfile.listFiles();
            if (null == fileList) {
                logger.warn("无权限删除目录：{}", dirfile.getAbsolutePath());
                return false;
            }
            for (File file : fileList) {
                if (file.isFile()) {
                    file.delete();
                } else {
                    // 递归删除
                    deletDir(file);
                }
            }
        }
        return dirfile.delete();
    }

    /**
     * @describe 创建文件
     * @author cp.ren
     * @date 2019-09-24 14:13:09
     * @param filePath
     * @return
     * @version V5.0
     **/
    public static File createFile(String filePath) throws IOException {
        File file = new File(filePath);
        if(!file.exists()) {
            file.mkdirs();
        }
        return file;
    }
}
