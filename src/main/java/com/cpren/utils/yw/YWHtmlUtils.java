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
}
