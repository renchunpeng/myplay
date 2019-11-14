package com.cpren.lucene;

import com.cpren.pojo.LuceneFieldData;
import com.cpren.pojo.Question;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.TextField;
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
//        // 问题类型
//        doc.add(new NumericDocValuesField(LuceneFieldData.LU_FIELD_QUESTION_TYPE, question.getType()));
//        // 知识配置
//        doc.add(new TextField(LuceneFieldData.LU_FIELD_KNOWLEDGE_FIELD, question.getFields(), Field.Store.YES));
        try {
            indexWriter.addDocument(doc);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
