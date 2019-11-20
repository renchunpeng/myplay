package com.cpren.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.StringReader;

/**
 * @author 任春鹏
 */
public class HighLightUtil {
    /**
     * 对一段文本执行语法高亮处理，这个感觉没啥用，不能解决html里的内容被高亮的问题，可以将分词结果返回前端，让js处理
     * @param doc 需要高亮的对象包含在其中Field=value
     * @param analyzer 分词器 默认采用IKAnalyzer
     * @param query QueryParser
     * @param field 需要高亮的字段，一般是value
     * @param simpleHTMLFormatter 高亮的css代码
     * @return 被高亮的html代码
     * @throws Exception 异常
     */
    public static String getHighLight(Document doc, Analyzer analyzer, Query query, String field, SimpleHTMLFormatter simpleHTMLFormatter) throws Exception{
        /* 语法高亮显示设置 */
        Highlighter highlighter = new Highlighter(simpleHTMLFormatter, new QueryScorer(query));
        highlighter.setTextFragmenter(new SimpleFragmenter(100));
        // 取 field 字段值，准备进行高亮
        String fieldValue = doc.get(field);
        TokenStream tokenStream = analyzer.tokenStream(field, new StringReader(fieldValue));
        // 转成高亮的值
        String highLightFieldValue = highlighter.getBestFragment(tokenStream, fieldValue);
        if (highLightFieldValue == null) {
            highLightFieldValue = fieldValue;
        }
        return highLightFieldValue;
    }

    public static void main(String[] args) throws Exception {
        SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<b>", "</b>");
        String term = "查询单词";
        QueryParser queryParser = new QueryParser("value", new IKAnalyzer());
        Query query = queryParser.parse(term);
        Document document = new Document();
        String value = "Lucene<h1 title='我的单词'>查询</h1> Lucene查询语法以可读的方式书写,然后使用JavaCC进行词法转换,转换成机器可识别" +
                "的查询. 下面着重介绍下Lucene支持的查询: Terms词语查询 词语搜索,支持 单词 和";
        document.add(new TextField("value", value, Field.Store.YES));
        String highLight = getHighLight(document, new IKAnalyzer(), query, "value", simpleHTMLFormatter);
        System.out.println(highLight);
    }

}
