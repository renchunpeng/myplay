package com.cpren;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.queryparser.flexible.standard.parser.Token;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author cdxu@iyunwen.com on 2019/10/14
 */
public class NlpTest {

    @Test
    public void test() throws IOException {
        Map<String,Integer> map = new HashMap<>();
        String keyWord = "java是一种可以撰写跨平台应用软件的面向对象的程序设计语言。";
        nlp(keyWord, map);
        System.out.println(map);
        String keyWord2 = "c++是一种可以撰写跨平台应用软件的";
        nlp(keyWord2, map);
        System.out.println(map);
    }

    private void nlp(String keyWord, Map<String,Integer> map) {
        IKAnalyzer analyzer = new IKAnalyzer();
        System.out.println("分词："+keyWord);
        try {
            TokenStream tokenStream = analyzer.tokenStream("content",
                    new StringReader(keyWord));
            tokenStream.addAttribute(CharTermAttribute.class);
            tokenStream.reset();//必须先调用reset方法，否则会报下面的错，可以参考TokenStream的API说明
            /* java.lang.IllegalStateException: TokenStream contract violation: reset()/close() call missing, reset() called multiple times, or subclass does not call super.reset(). Please see Javadocs of TokenStream class for more information about the correct consuming workflow.*/
            System.out.println("结果：");
            while (tokenStream.incrementToken()) {
                CharTermAttribute charTermAttribute = tokenStream.getAttribute(CharTermAttribute.class);
                System.out.print(charTermAttribute.toString() + " ");
                String s = charTermAttribute.toString();
                if(map.containsKey(s)) {
                    Integer integer = map.get(s);
                    map.put(s, ++integer);
                }else {
                    map.put(s,1);
                }
            }
            tokenStream.end();
            tokenStream.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
