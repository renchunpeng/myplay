package com.cpren.utils.yw;

import com.cpren.utils.yw.YWRegex;

import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 *
 * @author cdxu@iyunwen.com on 2018/2/8
 */
public class YWStringUtils {

    /** 中文符号正则表达式 */
    private static String regEx = "[\u4e00-\u9fa5]";
    /** 中文符号匹配Pattern */
    private static Pattern pat = Pattern.compile(regEx);
    /**
     * 禁止实例化
     */
    private YWStringUtils() {

    }

    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs != null && (strLen = cs.length()) != 0) {
            for (int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isNotBlank(CharSequence cs) {
        return !isBlank(cs);
    }

    public static String reverse(String str) {
        return str == null ? null : (new StringBuilder(str)).reverse().toString();
    }

    /**
     * 校验用户名是否合法
     *
     * @param username 用户名
     * @return true 合法 false 不合法
     */
    public static boolean isUserName(String username) {
        if (username == null) {
            return false;
        }
        return YWRegex.USERNAME_PATTERN.matcher(username).matches();
    }

    /**
     * 校验密码是否合法
     *
     * @param password 密码
     * @return true 合法 false 不合法
     */
    public static boolean isPassword(String password) {
        if (password == null) {
            return false;
        }
        return YWRegex.PASSWORD_PATTERN.matcher(password).matches();
    }

    /**
     * @param email 邮箱地址
     * @return true-合法，false-不合法
     * @describe 验证邮箱格式
     * @author y.you
     * @date 2018年10月15日 11:30
     * @version V5.0
     */
    public static boolean isEmail(String email) {
        if (isBlank(email)) {
            return false;
        }
        return YWRegex.EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * @param telNumber 手机号
     * @return true-合法，false-不合法
     * @describe 验证手机号格式
     * 中国电信号段 133、149、153、173、177、180、181、189、199
     * 中国联通号段 130、131、132、145、155、156、166、175、176、185、186
     * 中国移动号段 134(0-8)、135、136、137、138、139、147、150、151、152、157、158、159、178、182、183、184、187、188、198
     * --其他号段
     * 14号段以前为上网卡专属号段，如中国联通的是145，中国移动的是147等等。
     * @author y.you
     * @date 2018年10月17日 11:20
     * @version V5.0
     */
    public static boolean isTelNumber(String telNumber) {
        if (isBlank(telNumber) || telNumber.length() != 11) {
            return false;
        }
        return YWRegex.MOBILE_PATTERN.matcher(telNumber).matches();
    }

    /**
     * @param qq QQ号
     * @return true-合法，false-不合法
     * @describe 验证qq号格式
     * @author pfhe
     * @date 2018年12月20日 16:23
     * @version V5.0
     */
    public static boolean isQQ(String qq) {
        if (isBlank(qq)) {
            return false;
        }
        return YWRegex.QQ_PATTERN.matcher(qq).matches();
    }

    /**
     * @param idCard 身份证号码
     * @return true-合法，false-不合法
     * @describe 验证身份证号格式
     * @author pfhe
     * @date 2018年12月20日 16:23
     * @version V5.0
     */
    public static boolean isIdCard(String idCard) {
        if (isBlank(idCard)) {
            return false;
        }
        return YWRegex.IDCARD_PATTERN.matcher(idCard).matches();
    }

    /**
     * @param str
     * @param delimiter
     * @return
     * @describe 使用指定分隔符分隔字符串
     * @author y.you
     * @date 2018年10月15日 20:51
     * @version V5.0
     */
    public static String[] spliceString(String str, String delimiter) {
        if (isNotBlank(str)) {
            return str.split(delimiter);
        }
        return null;
    }

    /**
     * 字符串首字母转大写
     *
     * @param str 字符串
     * @return 返回首字母转大写的字符串
     */
    public static String toUpperFirstCase(String str) {

        if (isBlank(str)) {
            return null;
        }

        if (Character.isUpperCase(str.charAt(0))) {
            return str;
        } else {
            return (new StringBuilder()).append(Character.toUpperCase(str.charAt(0))).append(str.substring(1)).toString();
        }
    }

    public static String join(Object[] array, String separator) {
        if (array == null) {
            return null;
        }
        return join(array, separator, 0, array.length);
    }

    public static String join(Object[] array, char separator) {
        if (array == null) {
            return null;
        }
        return join(array, separator, 0, array.length);
    }

    public static String join(Object[] array, char separator, int startIndex, int endIndex) {
        if (array == null) {
            return null;
        }
        int bufSize = (endIndex - startIndex);
        if (bufSize <= 0) {
            return "";
        }
        bufSize *= ((array[startIndex] == null ? 16 : array[startIndex].toString().length()) + 1);
        StringBuilder buf = new StringBuilder(bufSize);
        for (int i = startIndex; i < endIndex; i++) {
            if (i > startIndex) {
                buf.append(separator);
            }
            if (array[i] != null) {
                buf.append(array[i]);
            }
        }
        return buf.toString();
    }

    public static String join(Object[] array, String separator, int startIndex, int endIndex) {
        if (array == null) {
            return null;
        }
        if (separator == null) {
            separator = "";
        }
        int bufSize = (endIndex - startIndex);
        if (bufSize <= 0) {
            return "";
        }
        bufSize *= ((array[startIndex] == null ? 16 : array[startIndex].toString().length())
                + separator.length());

        StringBuffer buf = new StringBuffer(bufSize);

        for (int i = startIndex; i < endIndex; i++) {
            if (i > startIndex) {
                buf.append(separator);
            }
            if (array[i] != null) {
                buf.append(array[i]);
            }
        }
        return buf.toString();
    }

    /**
     * @param content   文本内容
     * @param encodeing 编码格式
     * @return
     * @describe 将内容按照指定编码格式进行URLEncode编码
     * @author y.you
     * @date 2018年10月24日 10:45
     * @version V5.0
     */
    public static String urlEncode(String content, String encodeing) {
        String strValue = "";
        if (isBlank(content)) {
            return strValue;
        }
        try {
            if (isBlank(encodeing)) {
                strValue = URLEncoder.encode(content, "UTF-8");
            } else {
                strValue = URLEncoder.encode(content, encodeing);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strValue;
    }

    /**
     * 检查字符串中是否都是数字
     */
    public static boolean checkNum(String num){
        if (isBlank(num)){
            return false;
        }
        Pattern p = Pattern.compile("([0-9]+)");
        Matcher m = p.matcher(num.trim());
        return m.matches();
    }

    /**
     * 是否包含中文字符
     */
    public static boolean isContainsChinese(String str) {
        Matcher matcher = pat.matcher(str);
        boolean flg = false;
        if (matcher.find()) {
            flg = true;
        }
        return flg;
    }

    /**
     * 检查字符串中是否都是字母
     */
    public static boolean checkLetter(String letter){
        if (isBlank(letter)){
            return false;
        }
        Pattern p = Pattern.compile("[a-zA-Z]+");
        Matcher m = p.matcher(letter.trim());
        return m.matches();
    }

    /**
     * 检查字符串中是否包含字母
     */
    public static boolean isContainsLetter(String letter){
        if (isBlank(letter)){
            return false;
        }
        Pattern p = Pattern.compile("[a-zA-Z]+");
        Matcher m = p.matcher(letter.trim());

        if (m.find()){
            return true;
        }
        return false;
    }

	/**
     * 打印线程调用堆栈信息
     * @param statcks 堆栈信息
     * @return
     */
    public static String getStackTraces(StackTraceElement[] statcks){
        if(statcks == null || statcks.length == 0)
            return "";
        StringBuilder sbf = new StringBuilder();

        for(StackTraceElement stackTraceElement : statcks){
            sbf.append(stackTraceElement.getClassName());
            sbf.append(System.getProperty("line.separator"));
        }
        return sbf.toString();
    }

}
