package com.cpren;

import cn.hutool.Hutool;
import com.cpren.dao.LoginLogDao;
import com.cpren.lucene.LucencUtil;
import com.cpren.pojo.LoginLog;
import com.cpren.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.CallStackUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * @author cdxu@iyunwen.com on 2019/10/12
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest
@Slf4j
public class LoginLogTest {
    @Autowired
    private LoginLogDao loginLogDao;

    @Autowired
    private LucencUtil lucencUtil;

    @Test
    public void test() throws IOException {
        List<LoginLog> all = loginLogDao.getAll();
        lucencUtil.deleteIndex();
        IndexWriter indexWriter = lucencUtil.getIndexWriter();
        for(LoginLog item : all) {
            Document doc = new Document();
            // 问题
            doc.add(new LongField("user_id", item.getUser_id(), Field.Store.YES));
            doc.add(new TextField("user_name", item.getUser_name(), Field.Store.YES));
            doc.add(new LongField("login_time", item.getLogin_time().getTime(), Field.Store.YES));
            try {
                indexWriter.addDocument(doc);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        indexWriter.commit();
    }

    @Test
    public void test2() throws IOException {
        IndexSearcher indexSearcher = lucencUtil.getIndexSearcher();
//        Query query = new TermQuery(new Term("user_name", "city01"));
//        NumericRangeQuery<Long> login_log = NumericRangeQuery.newLongRange("login_time", DateUtil.lastWeek().getTime(), DateUtil.nextWeek().getTime(), true, false);
//        BooleanQuery.Builder booleanQuery = new BooleanQuery.Builder();
//        booleanQuery.add(query, BooleanClause.Occur.MUST);
//        booleanQuery.add(login_log, BooleanClause.Occur.MUST);
        WildcardQuery wildcardQuery = new WildcardQuery(new Term("user_name", "ci*"));

        TopDocs topDocs = indexSearcher.search(wildcardQuery, 10);
        System.out.println("查询结果的总条数：" + topDocs.totalHits);
        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
            Document document = indexSearcher.doc(scoreDoc.doc);
            //文件名称
            System.out.println(document.get("user_id"));
            //文件大小
            System.out.println(document.get("user_name"));
            //文件路径
            System.out.println(document.get("login_time"));
            System.out.println("----------------------------------");
        }
    }


    public static void main(String[] args) {
//        Map<Integer, User> map = new HashMap();
//        for(int i = 0; i< 10; i++) {
//            User user = new User();
//            user.setUserName("测试用户"+i);
//            map.put(i,user);
//        }
//        User user = map.get(5);
//        System.out.println(user);

    }
}
