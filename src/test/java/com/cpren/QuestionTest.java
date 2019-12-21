package com.cpren;

import com.cpren.dao.QuestionDao;
import com.cpren.lucene.LucencUtil;
import com.cpren.lucene.QuestionUtil;
import com.cpren.pojo.Question;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.*;
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
        List<Question> questions = questionDao.queryQuestion();
        try {
            for (Question question : questions) {
                questionUtil.addQuestionField(question);
            }
            lucencUtil.getIndexWriter().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 单个term分词搜索
     * @throws IOException
     * @throws ParseException
     */
    @Test
    public void testSearchIndex() throws IOException, ParseException {
        IndexSearcher indexSearcher = lucencUtil.getIndexSearcher();
        BooleanQuery.Builder booleanQuery = new BooleanQuery.Builder();

        QueryParser queryParser = new QueryParser("question", new IKAnalyzer());
        Query query = queryParser.parse("标准申请");
        booleanQuery.add(query, BooleanClause.Occur.MUST);

        MyCollector myCollector = new MyCollector();
        indexSearcher.search(booleanQuery.build(), myCollector);
        TopDocs topDocs = myCollector.getTopDocs(10);
        System.out.println("查询结果的总条数：" + topDocs.totalHits);

        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
            Document document = indexSearcher.doc(scoreDoc.doc);
            System.out.println(document.get("question"));
        }
    }

    /**
     * 高亮显示搜索结果摘要
     * 针对多个term
     * @throws ParseException
     * @throws IOException
     * @throws InvalidTokenOffsetsException
     */
    @Test
    public void HighlighterSearch() throws ParseException, IOException, InvalidTokenOffsetsException {
        String key = "地面距离";
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
            System.out.println("</br>");
            String answer=doc.get("answer");
            if(answer!=null){
                TokenStream tokenStream=analyzer.tokenStream("answer", new StringReader(answer));
                /**
                 * getBestFragment方法用于输出摘要（即权重大的内容）
                 */
                System.out.println(highlighter.getBestFragment(tokenStream, answer));
                System.out.println("</br>");
            }
        }
    }

}
