package com.cpren;

import com.cpren.utils.JsoupUtils;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.IOException;

//@RunWith(SpringRunner.class)
//@SpringBootTest
@Slf4j
public class JsoupTest {
    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect("http://www.shizongzui.cc/longzu/lz1.html").get();
        Elements valueByCss = JsoupUtils.getNodeByCss(doc, "#BookText");
        System.out.println(JsoupUtils.getValueFromText(valueByCss.get(0)));
    }

    @Test
    public void CreateIndex() {
        Document document = JsoupUtils.getDocument("http://longzu5.yunzhonggexiaoshuo.com/");
        Elements nodeByCss = JsoupUtils.getNodeByCss(document, "#clearfix");
        for (Element element : nodeByCss) {
            System.out.println(JsoupUtils.getValueFromAttributeKey(element, "href"));
        }
    }
}
