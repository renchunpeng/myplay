package com.cpren;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;

@Component
public class AyncTest {
    private IndexWriter indexWriter;

    private final String filePath = "D:\\rcp\\lucene";

    @Async
    public void dorun(String name) throws InterruptedException {
        if(name.equals("111")) {
            System.out.println(name + "方法开始执行");
            Thread.sleep(5*1000);
            System.out.println(name + "方法结束执行");
        }else {
            System.out.println(name + "方法开始执行");
            Thread.sleep(3*1000);
            System.out.println(name + "方法结束执行");
        }
    }

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

    @Async
    public void createDoc(IndexWriter indexWriter, Document document) {
        try {
            indexWriter.addDocument(document);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
