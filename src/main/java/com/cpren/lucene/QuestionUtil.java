package com.cpren.lucene;

import com.cpren.pojo.LuceneFieldData;
import com.cpren.pojo.Question;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author cdxu@iyunwen.com on 2019/9/5
 */
@Component
public class QuestionUtil {

    @Autowired
    private LucencUtil lucencUtil;

    public void addQuestionField(Question question) {
        IndexWriter indexWriter = lucencUtil.getIndexWriter();

        Document doc = new Document();
        // 问题唯一id
        doc.add(new LongField(LuceneFieldData.LU_FIELD_QUESTION_ID, question.getId(), Field.Store.YES));
        // 问题
        doc.add(new TextField(LuceneFieldData.LU_FIELD_QUESTION, question.getQuestion(), Field.Store.YES));
        // 问题答案
        doc.add(new TextField(LuceneFieldData.LU_FIELD_ANSWER, question.getAnswer() == null?"空值":question.getAnswer(), Field.Store.YES));
        try {
            indexWriter.addDocument(doc);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
