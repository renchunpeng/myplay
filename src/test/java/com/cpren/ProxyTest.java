package com.cpren;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.io.IOException;

public class ProxyTest {

    @Test
    public void test() {
//        String proxyHost = "192.168.1.82";
//        String proxyPort = "3128";
//
//        System.setProperty("http.proxyHost", proxyHost);
//        System.setProperty("http.proxyPort", proxyPort);
//
//        // 对https也开启代理
//        System.setProperty("https.proxyHost", proxyHost);
//        System.setProperty("https.proxyPort", proxyPort);
        try {
            Document doc = Jsoup.connect("https://www.baidu.com").get();
            System.out.println(doc.body().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
