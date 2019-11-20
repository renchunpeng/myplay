package com.cpren;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.highlight.*;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.StringReader;

//@RunWith(SpringRunner.class)
//@SpringBootTest
/**
 * 搜索工具类
 */
@Slf4j
public class Temp {

    private final static IKAnalyzer analyzer = new IKAnalyzer();
    private final static Formatter highlighter_formatter = new SimpleHTMLFormatter("<span class=\"highlight\">","</span>");
    /**
     * 对一段文本执行语法高亮处理
     * @param text 要处理高亮的文本
     * @param key 高亮的关键字
     * @return 返回格式化后的HTML文本
     */
    public static String highlight(String text, String key) {
        if(StringUtils.isBlank(key) || StringUtils.isBlank(text))
            return text;
        String result = null;
        try {
            key = QueryParser.escape(key.trim().toLowerCase());
            QueryScorer scorer = new QueryScorer(new TermQuery(new Term(null,QueryParser.escape(key))));
            Highlighter hig = new Highlighter(highlighter_formatter, scorer);
            TokenStream tokens = analyzer.tokenStream(null, new StringReader(text));
            result = hig.getBestFragment(tokens, text);
        } catch (Exception e) {
            log.error("Unabled to hightlight text", e);
        }
        return (result != null)?result:text;
    }

    public static  String getHighLight(Document doc, Analyzer analyzer, Query query, String field, SimpleHTMLFormatter simpleHTMLFormatter) throws Exception{
        /* 语法高亮显示设置 */
        Highlighter highlighter = new Highlighter(simpleHTMLFormatter, new QueryScorer(query));
        highlighter.setTextFragmenter(new SimpleFragmenter(100));
        // 取 field 字段值，准备进行高亮
        String fieldValue = doc.get(field);
        TokenStream tokenStream = analyzer.tokenStream(field, new StringReader(fieldValue));
        // 转成高亮的值
        String highLightFieldValue = highlighter.getBestFragment(tokenStream, fieldValue);
        if (highLightFieldValue == null)
            highLightFieldValue = fieldValue;
        return highLightFieldValue;
    }

    @Test
    public void test() throws Exception {
        SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<b>", "</b>");
        String term = "查询单词";
        QueryParser queryParser = new QueryParser("value", new IKAnalyzer());
        Query query = queryParser.parse(term);
        Document document = new Document();
        String value = "Lucene查询 Lucene查询语法以可读的方式书写,然后使用JavaCC进行词法转换,转换成机器可识别" +
                "的查询. 下面着重介绍下Lucene支持的查询: Terms词语查询 词语搜索,支持 单词 和";
        document.add(new TextField("value", value, Field.Store.YES));
        String highLight = getHighLight(document, new IKAnalyzer(), query, "value", simpleHTMLFormatter);
        System.out.println(highLight);
    }
}