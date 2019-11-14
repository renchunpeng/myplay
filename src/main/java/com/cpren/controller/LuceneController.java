package com.cpren.controller;

import com.cpren.lucene.FileLuceneUtil;
import com.cpren.pojo.User;
import com.cpren.lucene.LucencUtil;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.IOException;

/**
 * @author cdxu@iyunwen.com on 2019/9/23
 */
@RequestMapping("lucene")
@Controller
public class LuceneController {

    @Autowired
    private LucencUtil lucencUtil;

    @Autowired
    private FileLuceneUtil fileLuceneUtil;

    /**
     * @describe 创建文件索引
     * @author cp.ren
     * @date 2019-09-05 14:15:40
     * @return
     * @version V5.0
     **/
    @RequestMapping("createFileIndex")
    public void testFileLucene(String filePath) {
        try {
            fileLuceneUtil.createFileIndex(new File(filePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("create_user")
    public void create_user(@RequestBody User user) {
        try {
            IndexWriter indexWriter = lucencUtil.getIndexWriter();
            //创建document对象
            Document document = new Document();
            Field field = new TextField("name",user.getUserName(), Field.Store.YES);
            Field field2 = new TextField("age",user.getAge(), Field.Store.YES);
            Field field3 = new TextField("address",user.getAddress(), Field.Store.YES);
            document.add(field);
            document.add(field2);
            document.add(field3);
            indexWriter.addDocument(document);
            indexWriter.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("find")
    public void findFile(String name) throws IOException, ParseException {
        IndexSearcher indexSearcher = lucencUtil.getIndexSearcher();
        Query query = new TermQuery(new Term("name", name));
        TopDocs topDocs = indexSearcher.search(query, 10);
        System.out.println("查询结果的总条数：" + topDocs.totalHits);
        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
            Document document = indexSearcher.doc(scoreDoc.doc);
            System.out.println(document.get("name"));
            System.out.println(document.get("age"));
            System.out.println(document.get("address"));
        }
    }
}
