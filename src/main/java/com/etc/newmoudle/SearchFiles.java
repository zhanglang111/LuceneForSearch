package com.etc.newmoudle;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

public class SearchFiles {
    public static void main(String[] args) throws Exception {

        //默认使用的是标准分析器

        String indexPath = "D:/lucene_test/index"; // 建立索引文件的目录
        String field = "contents";

        //以读的方式打开索引库//指定索引库的位置
        IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(indexPath)));

        //搜索，参数就是reader
        IndexSearcher searcher = new IndexSearcher(reader);

        Analyzer analyzer = new StandardAnalyzer();

        BufferedReader in = null;
        in = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));


        //查询语句
        QueryParser parser = new QueryParser(field, analyzer);
        System.out.println("Enter query:");
        // 从Console读取要查询的语句
        String line = in.readLine();
        if (line == null || line.length() == -1) {
            return;
        }
        line = line.trim();
        if (line.length() == 0) {
            return;
        }
        //执行查询
        Query query = parser.parse(line);
        System.out.println("Searching for:" + query.toString(field));

        doPagingSearch(searcher, query);
        in.close();
        reader.close();
    }

    private static void doPagingSearch(IndexSearcher searcher, Query query) throws IOException {
        // TopDocs保存搜索结果


        //执行查询，参数2：返回的最大的数
        TopDocs results = searcher.search(query, 10);
        //相关度
        ScoreDoc[] hits = results.scoreDocs;
        //最后命中了对少条
        int numTotalHits = Math.toIntExact(results.totalHits);
        System.out.println(numTotalHits + " total matching documents");
        for (ScoreDoc hit : hits) {
            Document document = searcher.doc(hit.doc);//取文档的id,根据id找对象
            System.out.println("文档:" + document.get("path"));
            System.out.println("相关度:" + hit.score);
            System.out.println("内容:" + document.get("contents"));
            System.out.println("================================");
        }

    }


    @Test
    public void testTokenStream() throws Exception{
        Analyzer analyzer = new StandardAnalyzer();
        TokenStream tokenStream = analyzer.tokenStream("","the spring framework provide a ");
        CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
        tokenStream.reset();
        while (tokenStream.incrementToken()){
            System.out.println(charTermAttribute.toString());
        }

        tokenStream.close();
    }

    //对索引库进行增删改查（维护）
    //添加文档，

}

