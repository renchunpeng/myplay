package com.cpren;

import com.cpren.pojo.LuceneFieldData;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Temp {

//    @Autowired
//    private QuestionDao questionDao;

    @Autowired
    private AyncTest ayncTest;

    @Test
    public void test() throws InterruptedException, IOException {
        //        List<Question> questions = questionDao.rcp(20);
        //        System.out.println();
//        ayncTest.dorun("111");
//        ayncTest.dorun("222");
//        Thread.sleep(8*1000);
        for(int i=0;i<10;i++) {
            IndexWriter indexWriter = ayncTest.getIndexWriter();
            Document doc = new Document();
            // 问题唯一id
            doc.add(new LongField(LuceneFieldData.LU_FIELD_QUESTION_ID, i, Field.Store.YES));
            // 问题
            doc.add(new TextField(LuceneFieldData.LU_FIELD_QUESTION, String.valueOf(i), Field.Store.YES));
            ayncTest.createDoc(indexWriter, doc);
            indexWriter.close();
        }
    }
}

