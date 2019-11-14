package com.cpren.utils.yw;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author cdxu@iyunwen.com on 2018/11/16
 */
public interface YWRegex {

    /**
     * 5~30位英文字符，支持数字、大小写字母和下划线
     */
    String USERNAME = "^\\w{5,30}$";

    String COLUMN = "^\\p{Alpha}\\w{0,29}$";

    /**
     * 6~20位英文字符，支持数字、大小写字母和标点符号
     */
    String SECRET_PATTERN = "\\p{Graph}{6,20}";

    String MOBILE = "^((13[0-9])|(14[579])|(15[0-35-9])|(166)|(17[0-35-8])|(18[0-9])|(19[89]))\\d{8}$";

    String EMAIL = "^([\\w]+[\\.\\-]?)*[\\w]+@(\\w+\\.)+\\w+$";

    String QQ = "^\\d{5,20}$";

    String IDCARD = "(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)";

    String DUPLICATE_ENTRY = "\\s+'\\S*'\\s+";

    /**
     * 校验字符串只包含大小写英文字符、数字及下划线
     */
    String BASE_CHARACTER = "^[a-zA-Z0-9_]+$";

    /** IP校验正则 */
    String IP = "\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b";

    /** 中英文标点符号 */
    String PUNCTUATION  = "[`~!@#$^&*()=|{}':;',\\[\\].<>/?~\\-！@#￥……&*（）——|{}【】‘；：”“'。，、？]";

    /** 标点符号正则 */
    Pattern PUNCTUATION_PATTERN = Pattern.compile(PUNCTUATION);

    Pattern USERNAME_PATTERN = Pattern.compile(USERNAME);

    Pattern PASSWORD_PATTERN = Pattern.compile(SECRET_PATTERN);

    Pattern MOBILE_PATTERN = Pattern.compile(MOBILE);

    Pattern EMAIL_PATTERN = Pattern.compile(EMAIL);

    Pattern QQ_PATTERN = Pattern.compile(QQ);

    Pattern IDCARD_PATTERN = Pattern.compile(IDCARD);

    Pattern DUPLICATE_ENTRY_PATTERN = Pattern.compile(DUPLICATE_ENTRY);

    Pattern IP_PATTERN = Pattern.compile(IP);

    Pattern COLUMN_PATTERN = Pattern.compile(COLUMN);

    /**
     * 获取违反唯一性约束的值
     *
     * @param duplicateKeyExceptionMessage 违反唯一性约束异常消息
     * @return 违反唯一性约束的值
     */
    static String duplicateEntryVal(String duplicateKeyExceptionMessage) {
        Matcher matcher = DUPLICATE_ENTRY_PATTERN.matcher(duplicateKeyExceptionMessage);
        String str = null;
        while (matcher.find()) {
            str = matcher.group();
        }
        return str;
    }

    public static void main(String[] args) {
    	boolean flag = Pattern.compile(BASE_CHARACTER).matcher("").matches();
    	System.out.println(flag);
	}

}
