package com.cpren.utils.yw;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author hjgu@iyunwen.com
 * @Date 2018/10/24 14:41
 */
public class YWHtmlUtils {
    private static final String REG_BODY = "<BODY[^>]*>([\\s\\S]*)<\\/BODY>";

    public static String getBadyStr(String html) {
        Pattern pattern = Pattern.compile(REG_BODY);
        Matcher matcher = pattern.matcher(html);
        if (matcher.find()) {
        	return matcher.group(1);
        }
        return null;
    }

    public static List<HashMap<String, String>> getImgStr(String htmlStr) {
        List<HashMap<String, String>> pics = new ArrayList<HashMap<String, String>>();
        Document doc = Jsoup.parse(htmlStr);
        Elements imgs = doc.select("img");
        for (Element img : imgs) {
            HashMap<String, String> map = new HashMap<String, String>();
            /*if (!"".equals(img.attr("WIDTH"))) {
                map.put("WIDTH", img.attr("WIDTH").substring(0, img.attr("WIDTH").length() - 2));
            }
            if (!"".equals(img.attr("HEIGHT"))) {
                System.out.println(img.attr("HEIGHT"));
                map.put("HEIGHT", img.attr("HEIGHT").substring(0, img.attr("HEIGHT").length() - 2));
            }*/
            map.put("SRC", img.attr("src"));
            pics.add(map);
        }
        return pics;
    }

    /**
     * 提取html中纯文本
     * @param strHtml html字符串
     * @return
     */
    public static String html2Text(String strHtml){
        // 剔除<html>标签
        String txt = strHtml.replaceAll("</?[^>]+>", "");
        // 去除字符串中的空格，回车，换行符，制表符
        txt = txt.replaceAll("<a>\\s*|\t|\r|\n</a>", "");
        return txt;
    }

    /**
     * 正则替换html
     * @param inputString 需要被转化的html字符串
     * @return
     */
    public static String html3Text(String inputString) {
        // 含html标签的字符串
        String htmlStr = inputString;
        String textStr = "";
        java.util.regex.Pattern p_script;
        java.util.regex.Matcher m_script;
        java.util.regex.Pattern p_style;
        java.util.regex.Matcher m_style;
        java.util.regex.Pattern p_html;
        java.util.regex.Matcher m_html;
        try {
            // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
            // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
            // 定义HTML标签的正则表达式
            String regEx_html = "<[^>]+>";
            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            // 过滤script标签
            htmlStr = m_script.replaceAll("");
            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            // 过滤style标签
            htmlStr = m_style.replaceAll("");
            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            // 过滤html标签
            htmlStr = m_html.replaceAll("");
            textStr = htmlStr;
        } catch (Exception e) {System.err.println("Html2Text: " + e.getMessage()); }
        //剔除空格行
        textStr=textStr.replaceAll("[ ]+", " ");
        textStr=textStr.replaceAll("(?m)^\\s*$(\\n|\\r\\n)", "");
        // 返回文本字符串
        return textStr;
    }
}
