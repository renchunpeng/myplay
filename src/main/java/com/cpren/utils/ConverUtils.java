package com.cpren.utils;

import cn.hutool.core.io.FileUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

public class ConverUtils {
    public static void main(String[] args) {
        String s = fileUnicodeToString("D:/test.properties");
        System.out.println(s);
    }

    /**
     * 将unicode字符串转化为utf-8中文字符串
     * @param str 待转化unicode字符串
     * @return 转化完成的字符串
     */
    public static String unicodeToString(String str) {
        Pattern pattern = compile("(\\\\u(\\p{XDigit}{4}))");
        Matcher matcher = pattern.matcher(str);
        char ch;
        while (matcher.find()) {
            //group 6728
            String group = matcher.group(2);
            //ch:'木' 26408
            ch = (char) Integer.parseInt(group, 16);
            //group1 \u6728
            String group1 = matcher.group(1);
            str = str.replace(group1, ch + "");
        }
        return str;
    }

    /**
     * 将文件内容整体由Unicode编码转化为中文字符串
     * @param path 待转化文件路径
     * @return 转化完成的文件内容字符串
     */
    private static String fileUnicodeToString(String path) {
        String s = FileUtil.readString(path, "UTF-8");
        String s1 = unicodeToString(s);
        return s1;
    }
}