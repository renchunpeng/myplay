package com.cpren.utils.yw;

import java.util.Scanner;

/**
 * 字符编码工具类
 *
 * @author cdxu@iyunwen.com on 2018/9/26
 */
public class YWUnicodeUtils {

    /**
     * 键盘上最小ASCII编码字符 !
     */
    private static final char ASCII_MIN = 0x21;

    /**
     * 键盘上最大ASCII编码字符 ~
     */
    private static final char ASCII_MAX = 0x7E;

    /**
     * 禁止实例化
     */
    private YWUnicodeUtils() {

    }

    /**
     * 字符串转Unicode编码 保留ASCII 码在33~126之间的字符
     *
     * @param str 需要转换的字符串
     * @return 转换后的Unicode编码
     */
    public static String stringToUnicode(String str) {
        StringBuilder sb = new StringBuilder();
        if (YWStringUtils.isBlank(str)) {
        	return sb.toString();
        }
        char[] chars = str.toCharArray();
        for (char c : chars) {
            if (ASCII_MIN <= c && c <= ASCII_MAX) {
                sb.append(c);
            } else {
                sb.append("\\u").append(Integer.toHexString(c).toUpperCase());
            }
        }
        return sb.toString();
    }

    /**
     * Unicode编码 还原成字符串
     *
     * @param unicodeStr Unicode编码
     * @return 还原后的字符串
     */
    public static String unicodeToString(String unicodeStr) {
        StringBuilder sb = new StringBuilder();
        String[] chars = unicodeStr.split("\\\\u");
        for (String unicode : chars) {
            if (YWStringUtils.isNotEmpty(unicode)) {
                String ascii;
                if (unicode.length() >= 4) {
                    String noneAscii = unicode.substring(0, 4);
                    ascii = unicode.substring(4);
                    int hex = Integer.valueOf(noneAscii, 16);
                    sb.append((char) hex);
                } else {
                    ascii = unicode;
                }
                sb.append(ascii);
            }
        }
        return sb.toString();
    }

    /**
     * 支持命令行传参
     *
     * @param args 需要转换的字符串 只取第一个参数
     */
    @SuppressWarnings("resource")
    public static void main(String[] args) {
        String str = (args == null || args.length == 0) ? "" : args[0];

        while (YWStringUtils.isNotBlank(str)) {
            System.out.println("请输入需要转换成Unicode的字符：");
            Scanner sc = new Scanner(System.in);
            str = sc.nextLine();
        }

    	String unicodeStr = stringToUnicode(str);
    	str = unicodeToString(unicodeStr);

    	System.out.println("stringToUnicode: " + unicodeStr);
    	System.out.println("unicodeToString: " + str);
    }
}
