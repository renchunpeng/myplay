package com.cpren;

import com.cpren.dao.QuestionDao;
import com.cpren.lucene.LucencUtil;
import com.cpren.lucene.QuestionUtil;
import com.cpren.pojo.Question;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

/**
 * @author cdxu@iyunwen.com on 2019/8/27
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class QuestionTest {

    @Autowired
    private LucencUtil lucencUtil;

    @Autowired
    private QuestionUtil questionUtil;

    @Autowired
    private QuestionDao questionDao;

    /**
     * @describe 创建问题索引
     * @author cp.ren
     * @date 2019-09-05 14:16:16
     * @return
     * @version V5.0
     **/
    @Test
    public void testCreateIndex() {
        boolean b = lucencUtil.deleteIndex();
        if(b) {
            log.warn("索引清除成功!");
        }
        List<Question> questions = questionDao.queryAllQuestion();
        try {
            for (Question question : questions) {
                questionUtil.addQuestionField(question);
            }
            lucencUtil.getIndexWriter().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSearchIndex() throws IOException, ParseException {
        IndexSearcher indexSearcher = lucencUtil.getIndexSearcher();
        BooleanQuery.Builder booleanQuery = new BooleanQuery.Builder();

        QueryParser queryParser = new QueryParser("question", new IKAnalyzer());
        Query query = queryParser.parse("资产分界点");
        booleanQuery.add(query, BooleanClause.Occur.MUST);


        TopDocs topDocs = indexSearcher.search(booleanQuery.build(), 1000);
        System.out.println("查询结果的总条数：" + topDocs.totalHits);

        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
            Document document = indexSearcher.doc(scoreDoc.doc);
            System.out.println(document.get("question"));
        }
    }

    /**
     * 关键命中词高亮输出处理
     * @param query
     * @param context
     * @return
     * @throws Exception
     */
    public static String getHighlighterString(Query query,String context)throws Exception{
        //对促成文档匹配的实际项进行评分
        QueryScorer scorer=new QueryScorer(query);
        //设置高亮的HTML标签格式
        Formatter simpleHTMLFormatter=new SimpleHTMLFormatter("","");
        //实例化高亮分析器
        Highlighter highlighter=new Highlighter(simpleHTMLFormatter,scorer);
        //提供静态方法，支持从数据源中获取TokenStream，进行token处理
        TokenStream tokenStream=new CJKAnalyzer().tokenStream("question", new StringReader(context));
        return highlighter.getBestFragment(tokenStream, context);
    }

    @Test
    public void searcherTest()throws Exception{
        //  Indexer();
        IndexSearcher is = lucencUtil.getIndexSearcher();
        QueryParser qp=new QueryParser("question",new IKAnalyzer());
        String q="资产分界点";
        Query query=qp.parse(q);
        TopDocs tDocs=is.search(query,11);
        System.out.println("查询-》"+q+"《-总共命中【"+tDocs.totalHits+"】条结果");
        for (ScoreDoc scoredoc:tDocs.scoreDocs){
            Document doc = is.doc(scoredoc.doc);
            String context=doc.get("question");
            if(context!=null){
                System.out.println(getHighlighterString(query,context));
            }

        }
    }

}
