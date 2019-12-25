package com.etc.newmoudle;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

import java.nio.file.Paths;

public class indexManager {

    @Test
    public void addDocument() throws Exception {

        String indexPath = "D:/lucene_test/index";

        Analyzer analyzer = new StandardAnalyzer();

        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);

        Directory dir = FSDirectory.open(Paths.get(indexPath));//指定索引文件的位置

        IndexWriter indexWriter = new IndexWriter(dir, iwc);

        Document document = new Document();

        document.add(new TextField("path", "新添加的文件", Field.Store.YES));
        document.add(new TextField("contents", "新添加的文件类容", Field.Store.NO));

        //写入索引库
        indexWriter.addDocument(document);
        indexWriter.close();
    }
}


