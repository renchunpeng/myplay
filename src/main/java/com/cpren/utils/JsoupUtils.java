package com.cpren.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * 适用Jsoup获取网页的信息
 * @author 任春鹏
 */
public class JsoupUtils {

    /**
     * 获取网页本体Document
     * @param url
     * @return
     */
    public static Document getDocument(String url) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
            return doc;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }

    /**
     * 根据css选择器获取节点
     * @param doc
     * @param cssQuery
     * @return
     */
    public static Elements getNodeByCss(Document doc, String cssQuery) {
        Elements links = doc.select(cssQuery);
        return  links;
    }

    /**
     * 根据css选择器获取节点
     * @param elements
     * @param cssQuery
     * @return
     */
    public static Elements getNodeByCss(Elements elements, String cssQuery) {
        Elements links = elements.select(cssQuery);
        return  links;
    }

    /**
     * 根据attributeKey获取属性值
     * @param element
     * @param attributeKey
     * @return
     */
    public static String getValueFromAttributeKey(Element element, String attributeKey) {
        String attr = element.attr(attributeKey);
        return attr;
    }

    /**
     * 获取节点的text内容
     * @param element
     * @return
     */
    public static String getValueFromText(Element element) {
        String val = element.text();
        return val;
    }
}
