package com.cpren.lucene;

import cn.hutool.core.io.FileUtil;
import lombok.Synchronized;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.stereotype.Component;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;


/**
 * @author cp.ren
 * @version V5.0
 * @describe Lucene工具类
 * @date 2019-09-05 09:17:12
 **/
@Component
public class LucencUtil {

    private final String filePath = "D:\\rcp\\lucene";

    private IndexWriter indexWriter;

    private IndexReader indexReader;

    private IndexSearcher indexSearcher;

    /**
     * @describe 获取IndexWriter
     * @author cp.ren
     * @date 2019-09-05 14:00:05
     * @return
     * @version V5.0
     **/
    @Synchronized
    public IndexWriter getIndexWriter() {
        if (null != indexWriter) {
            return indexWriter;
        } else {
            try {
                File dfile = new File(filePath);
                Directory directory = FSDirectory.open(dfile.toPath());
                Analyzer analyzer =  new IKAnalyzer();
                IndexWriterConfig config = new IndexWriterConfig(analyzer);
                indexWriter = new IndexWriter(directory, config);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return indexWriter;
        }
    }

    /**
     * @describe 获取IndexSearcher
     *  如果不为空判断索引目录是否发生变化，如果变化，关闭之前的重新赋值
     *  如果为空新创建
     * @author cp.ren
     * @date 2019-09-05 13:59:22
     * @return
     * @version V5.0
     **/
    public IndexSearcher getIndexSearcher() throws IOException {
        if (null != indexSearcher) {
            DirectoryReader changeReader = DirectoryReader.openIfChanged((DirectoryReader) indexReader);
            if(null != changeReader) {
                indexReader.close();
                indexReader = changeReader;
                indexSearcher = new IndexSearcher(indexReader);
            }
            return indexSearcher;
        } else {
            try {
                Directory directory = FSDirectory.open(new File(filePath).toPath());
                indexReader = DirectoryReader.open(directory);
                indexSearcher = new IndexSearcher(indexReader);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return indexSearcher;
        }
    }

    /**
     * @describe 获取lucene索引地址
     * @author cp.ren
     * @date 2019-09-24 13:34:09
     * @return
     * @version V5.0
     **/
    public String getLucenePath() {
        return this.filePath;
    }

    /**
     * @describe 清除系统lucene索引
     * @author cp.ren
     * @date 2019-09-24 13:34:54
     * @return
     * @version V5.0
     **/
    public boolean deleteIndex() {
        boolean del = FileUtil.del(getLucenePath());
        return del;
    }
}

