package com.cpren;

import cn.hutool.json.JSONUtil;
import com.cpren.dao.ExamQuestionDao;
import com.cpren.dao.QuestionDao;
import com.cpren.lucene.LucencUtil;
import com.cpren.lucene.QuestionUtil;
import com.cpren.pojo.Examquestion;
import com.cpren.pojo.LuceneFieldData;
import com.cpren.pojo.Question;
import com.cpren.utils.pageHelper.PageHelperUtils;
import com.cpren.utils.pageHelper.Pagination;
import com.cpren.utils.yw.YWJsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cdxu@iyunwen.com on 2019/8/27
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ExamQuestionTest {

    @Autowired
    private LucencUtil lucencUtil;

    @Autowired
    private QuestionUtil questionUtil;

    @Autowired
    private ExamQuestionDao examQuestionDao;

    /**
     * @describe 创建考题索引
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
        List<Examquestion> questions = examQuestionDao.queryAllQuestion();
        try {
            for (Examquestion question : questions) {
                IndexWriter indexWriter = lucencUtil.getIndexWriter();

                Document doc = new Document();
                // 考题唯一id
                doc.add(new LongField("id", question.getId(), Field.Store.YES));
                // 考题
                doc.add(new TextField("examQuestion", question.getExamQuestion(), Field.Store.YES));
                try {
                    indexWriter.addDocument(doc);
                } catch (IOException e) {
                    e.printStackTrace();
                }
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

        QueryParser queryParser = new QueryParser("examQuestion", new IKAnalyzer());
        Query query = queryParser.parse("供电");
        booleanQuery.add(query, BooleanClause.Occur.MUST);

        TopDocs topDocs = indexSearcher.search(booleanQuery.build(), 10);
        List<Examquestion> lists = new ArrayList<>();

        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
            Examquestion examquestion = new Examquestion();
            Document document = indexSearcher.doc(scoreDoc.doc);
            examquestion.setId(Long.valueOf(document.get("id")));
            examquestion.setExamQuestion(document.get("examQuestion"));
            lists.add(examquestion);
        }

        // 六,组建分页结果
        Pagination<Examquestion> examquestionPagination = PageHelperUtils.pagResult(1, 10, topDocs.totalHits, lists);
        System.out.println(YWJsonUtils.getMapper().writeValueAsString(examquestionPagination));
    }

}
