package com.etc.newmoudle.Service.ServiceImpl;

import com.etc.newmoudle.Bean.ConstantInPro;
import com.etc.newmoudle.Bean.MyCommandLineRunner;
import com.etc.newmoudle.Service.SearchService;
import com.etc.newmoudle.Utils.TopDocsUtil;
import com.etc.newmoudle.VO.OutputTest;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.StringReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    ConstantInPro constantInPro;

    @Autowired

    MyCommandLineRunner myCommandLineRunner;

    @Autowired
    TopDocsUtil topDocsUtil;

    @Override
    public List<OutputTest> CommonSearch(String SearchText) throws Exception {
        Query query=new TermQuery(new Term("fileContent",SearchText));
        return topDocsUtil.higtlight(myCommandLineRunner.reader,myCommandLineRunner.indexSearcher,query);
    }

    @Override
    public List<OutputTest> BooleanSearch(String[] strings) throws Exception {

        //写一个循环
        BooleanQuery.Builder builder = new BooleanQuery.Builder();
        for (int i = 0; i < strings.length; i++) {

            String QueryTest = strings[i].substring(1);
            TermQuery termQuery = new TermQuery(new Term("fileContent",QueryTest));
            if (strings[i].startsWith("+")) {
                builder.add(termQuery, BooleanClause.Occur.MUST);
            } else if (strings[i].startsWith("-")) {
                builder.add(termQuery, BooleanClause.Occur.MUST_NOT);
            }
        }
        BooleanQuery booleanQuery = builder.build();
        return topDocsUtil.higtlight(myCommandLineRunner.reader,myCommandLineRunner.indexSearcher,booleanQuery);
    }


    @Override
    public void InspecificDomainSearch() {

    }

    @Override
    public List<OutputTest> WildcardSearch2(String SearchText) throws Exception {
        WildcardQuery wildcardQuery = new WildcardQuery(new Term("fileContent", SearchText));
        return topDocsUtil.higtlight(myCommandLineRunner.reader,myCommandLineRunner.indexSearcher,wildcardQuery);
//        return topDocsUtil.getList(myCommandLineRunner.reader,myCommandLineRunner.indexSearcher,wildcardQuery);
    }

    //高亮显示那部分
    @Override
    public List<OutputTest> WildcardSearch(String SearchText) throws Exception{

        Analyzer analyzer = new StandardAnalyzer();
        QueryParser parser = new QueryParser("fileContent", analyzer);
        Query query = parser.parse(SearchText);

        long startTime = System.currentTimeMillis();
        //开始查询，查询前10条数据，将记录保存在docs中


        long endTime = System.currentTimeMillis();
        long costTime = endTime - startTime;
        System.out.println("查询时间消耗："+costTime);

        return topDocsUtil.higtlight(myCommandLineRunner.reader,myCommandLineRunner.indexSearcher,query);
    }

    @Test
    public void testMultiFiledQueryParser()throws Exception {

        IndexSearcher indexSearcher = myCommandLineRunner.indexSearcher;

//可以指定默认搜索的域是多个

        String[] fields = {"fileName","fileContent"};

//创建一个MulitFiledQueryParser对象

        MultiFieldQueryParser queryParser = new MultiFieldQueryParser(fields,new StandardAnalyzer());

        Query query = queryParser.parse("test");

        System.out.println(query);

//执行查询

        System.out.println("将要搜索"+query);
        TopDocs topDocs = indexSearcher.search(query, 10);

        ScoreDoc scoreDocs[] = topDocs.scoreDocs;

        ArrayList<OutputTest> outputTests = new ArrayList<>();
        for (ScoreDoc scoreDoc : scoreDocs) {
            Document doc = indexSearcher.doc(scoreDoc.doc);
            OutputTest outputTest = new OutputTest(doc.get("filePath"),scoreDoc.score,doc.get("fileName"),doc.get("fileContent"));
            outputTests.add(outputTest);
        }
        myCommandLineRunner.reader.close();
        System.out.println(outputTests);
    }



    @Test
    public void testOperator() throws Exception {

        TopDocsUtil topDocsUtil = new TopDocsUtil();

        String indexPath = "D:/lucene_test/index1";
        Directory directory = FSDirectory.open(Paths.get(indexPath));
        IndexReader reader = DirectoryReader.open(directory);
        IndexSearcher indexSearcher = new IndexSearcher(reader);

        String searchWords = "fileName:test* AND fileContent:hello";

        Analyzer analyzer = new StandardAnalyzer();
        Query query;
        QueryParser parser = new QueryParser("fileContent", analyzer);
        query = parser.parse(searchWords);

        List<OutputTest> list = topDocsUtil.higtlight(reader,indexSearcher,query);
        System.out.println(list);

        reader.close();
    }
}
