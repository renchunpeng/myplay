package com.cpren.lucene;

import com.cpren.lucene.LucencUtil;
import org.apache.commons.io.FileUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;

/**
 * @describe 文件相关的lucene索引
 * @author cp.ren
 * @date 2019-09-24 13:38:13
 * @version V5.0
 **/
@Component
public class FileLuceneUtil {

    @Autowired
    LucencUtil lucencUtil;

    /**
     * @describe 根据传入的文件路径递归创建文件索引
     * @author cp.ren
     * @date 2019-09-05 09:26:50
     * @version V5.0
     **/
    public void createFileIndex(File file) throws Exception {
        IndexWriter indexWriter = lucencUtil.getIndexWriter();
        findFile(indexWriter, file);
        indexWriter.commit();
    }

    /**
     * @describe 递归查找文件名
     * @author cp.ren
     * @date 2019-09-05 14:11:31
     * @param indexWriter
     * @param file
     * @return
     * @version V5.0
     **/
    private void findFile(IndexWriter indexWriter, File file) throws IOException {
        if (file.isFile()){
            Document indexableFields = buildFileDocument(file);
            try {
                indexWriter.addDocument(indexableFields);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            File[] fileList = file.listFiles();
            if(StringUtils.isEmpty(fileList)){
                return;
            }
            for (File file2 : fileList) {
                findFile(indexWriter, file2);
            }
        }
    }

    /**
     * @describe 构建文件document对象
     * @author cp.ren
     * @date 2019-09-05 14:12:49
     * @param file2
     * @return
     * @version V5.0
     **/
    private Document buildFileDocument(File file2) {
        //创建document对象
        Document document = new Document();
        //文件名称
        String fileName = file2.getName();
        //创建文件名域
        //第一个参数：域的名称
        //第二个参数：域的内容
        //第三个参数：是否存储
        Field fileNameField = new TextField("fileName", fileName, Field.Store.YES);
        //文件的大小
        long fileSize = FileUtils.sizeOf(file2);
        //文件大小域
        Field fileSizeField = new LongField("fileSize", fileSize, Field.Store.YES);
        //文件路径
        String filePath = file2.getPath();
        //文件路径域（不分析、不索引、只存储）
        Field filePathField = new StoredField("filePath", filePath);
        System.out.println("创建索引成功，索引地址:" + filePath);

        document.add(fileNameField);
        document.add(fileSizeField);
        document.add(filePathField);
        return document;
    }

    /**
     * @param value 搜索值
     * @return
     * @describe lucene搜索
     * @author cp.ren
     * @date 2019-09-05 09:36:55
     * @version V5.0
     **/
    public void searchFileIndex(String value, Integer num) throws IOException {
        IndexSearcher indexSearcher = lucencUtil.getIndexSearcher();
        Query query = new TermQuery(new Term("fileName", value));
        TopDocs topDocs = indexSearcher.search(query, num);
        soutValue(indexSearcher, topDocs);
    }

    private void soutValue(IndexSearcher indexSearcher, TopDocs topDocs) throws IOException {
        System.out.println("查询结果的总条数：" + topDocs.totalHits);
        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
            Document document = indexSearcher.doc(scoreDoc.doc);
            //文件名称
            System.out.println(document.get("fileName"));
            //文件大小
            System.out.println(document.get("fileSize"));
            //文件路径
            System.out.println(document.get("filePath"));
            System.out.println("----------------------------------");
        }
    }

    /**
     * @describe 模糊查询
     * @author cp.ren
     * @date 2019-09-05 15:34:13
     * @param value
     * @param num
     * @return
     * @version V5.0
     **/
    public void queryParserSearch(String value,Integer num) throws IOException, ParseException {
        IndexSearcher indexSearcher = lucencUtil.getIndexSearcher();
        QueryParser queryParser = new QueryParser("fileName", new IKAnalyzer());
        //使用默认的域,这里用的是语法，下面会详细讲解一下
        Query query = queryParser.parse(value);

        //执行查询
        //第一个参数是查询对象，第二个参数是查询结果返回的最大值
        TopDocs topDocs = indexSearcher.search(query, num);
        //查询结果的总条数
        soutValue(indexSearcher, topDocs);
    }

}
