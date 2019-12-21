package com.cpren;

import org.apache.lucene.index.LeafReaderContext;
import org.apache.lucene.search.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * lucene的收集器，在查询完成之后，返回数据之前可以对查询结果做一些操作
 */
public class MyCollector implements Collector, LeafCollector {

    private List<ScoreDoc> docs = new ArrayList<ScoreDoc>();
    private int docBase = 0;
    private Scorer scorer;

    public TopDocs getTopDocs(int end) {
        ScoreDoc[] scoreDocs = new ScoreDoc[Math.min(docs.size(), end)];
        for(int i=0;i<docs.size();i++) {
            scoreDocs[i] = docs.get(i);
        }
        return new TopDocs(docs.size(), scoreDocs, scoreDocs[0].score);
    }

    @Override
    public LeafCollector getLeafCollector(LeafReaderContext context) throws IOException {
        this.docBase = context.docBase;
        return this;
    }

    @Override
    public boolean needsScores() {
        return false;
    }

    @Override
    public void setScorer(Scorer scorer) throws IOException {
        this.scorer = scorer; //记录改匹配的打分情况
    }

    @Override
    public void collect(int doc) throws IOException {
        System.out.println("收集器起效果了"+doc);
        System.out.println(scorer.score());
        docs.add(new ScoreDoc(doc + docBase,scorer.score()));
    }
}
