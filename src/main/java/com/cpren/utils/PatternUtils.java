package com.cpren.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author cdxu@iyunwen.com on 2019/9/9
 */
public class PatternUtils {

    private static void getBookName(String content) {
        String regx = "《(.*?)》";
        Pattern pattern = Pattern.compile(regx);
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            System.out.println(matcher.group(1));
        }
    }

    public static void main(String[] args) {
        String content = "二维表附件为别人返利网《rcp》额无法开机不忘记开了方便让我离开《wq》《777》的价位货到付款角色列表对方即可勒索病毒";
        getBookName(content);
    }
}
