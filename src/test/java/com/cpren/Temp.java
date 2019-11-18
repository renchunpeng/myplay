package com.cpren;

import com.cpren.lucene.LucencUtil;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.io.StringReader;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Temp {
    @Autowired
    private LucencUtil lucencUtil;


    @Test
    public void search() throws ParseException, IOException, InvalidTokenOffsetsException {
        String key = "标准申请";
        IndexSearcher indexSearcher = lucencUtil.getIndexSearcher();

        IKAnalyzer analyzer=new IKAnalyzer();
        //可以指定默认搜索的域是多个
        String[] fields = {"question","answer"};
        //创建一个MulitFiledQueryParser对象
        MultiFieldQueryParser parser = new MultiFieldQueryParser(fields,analyzer);
        Query query=parser.parse(key);

        long start=System.currentTimeMillis();
        TopDocs hits=indexSearcher.search(query, 10);
        long end=System.currentTimeMillis();
        System.out.println("匹配 "+key+" ，总共花费"+(end-start)+"毫秒"+"查询到"+hits.totalHits+"个记录");

        QueryScorer scorer=new QueryScorer(query);
        Fragmenter fragmenter=new SimpleSpanFragmenter(scorer);
        SimpleHTMLFormatter simpleHTMLFormatter=new SimpleHTMLFormatter("<b><font color='red'>","</font></b>");
        Highlighter highlighter=new Highlighter(simpleHTMLFormatter, scorer);
        highlighter.setTextFragmenter(fragmenter);
        for(ScoreDoc scoreDoc:hits.scoreDocs){
            Document doc=indexSearcher.doc(scoreDoc.doc);
            System.out.println(doc.get("question"));
            String answer=doc.get("answer");
            if(answer!=null){
                TokenStream tokenStream=analyzer.tokenStream("answer", new StringReader(answer));
                /**
                 * getBestFragment方法用于输出摘要（即权重大的内容）
                 */
                System.out.println(highlighter.getBestFragment(tokenStream, answer));
            }
        }
    }

}