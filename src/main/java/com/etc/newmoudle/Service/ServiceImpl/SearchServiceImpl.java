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
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
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
        return topDocsUtil.getList(myCommandLineRunner.reader,myCommandLineRunner.indexSearcher,query);
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
        return topDocsUtil.getList(myCommandLineRunner.reader,myCommandLineRunner.indexSearcher,booleanQuery);
    }


    @Override
    public void InspecificDomainSearch() {

    }

    @Override
    public List<OutputTest> WildcardSearch2(String SearchText) throws Exception {
        WildcardQuery wildcardQuery = new WildcardQuery(new Term("fileContent", SearchText));
        return topDocsUtil.getList(myCommandLineRunner.reader,myCommandLineRunner.indexSearcher,wildcardQuery);
    }

    //高亮显示那部分
    @Override
    public List<String> WildcardSearch(String SearchText) throws Exception{

        String indexPath = "D:/lucene_test/index1";
        Directory directory = FSDirectory.open(Paths.get(indexPath));
        IndexReader reader = DirectoryReader.open(directory);
        IndexSearcher indexSearcher = new IndexSearcher(reader);

        Analyzer analyzer = new StandardAnalyzer();
//        SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();
        QueryParser parser = new QueryParser("fileContent", analyzer);
        Query query = parser.parse(SearchText);

        long startTime = System.currentTimeMillis();
        //开始查询，查询前10条数据，将记录保存在docs中
        TopDocs topDocs = indexSearcher.search(query, 4);
        //记录索引结束时间
        long endTime = System.currentTimeMillis();
        long costTime = endTime - startTime;
        System.out.println("查询时间消耗："+costTime);

        SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<b><font color=red>","</font></b>");

        QueryScorer scorer = new QueryScorer(query);
        //根据这个得分计算出一个片段
        Fragmenter fragmenter = new SimpleSpanFragmenter(scorer);
        //将这个片段中的关键字用上面初始化好的高亮格式高亮
        Highlighter highlighter = new Highlighter(simpleHTMLFormatter, scorer);

        highlighter.setTextFragmenter(fragmenter);

        ScoreDoc scoreDocs[] = topDocs.scoreDocs;
        //返回数据

        List<String> list = new ArrayList<>();
        for (ScoreDoc scoreDoc : scoreDocs) {
            Document document = indexSearcher.doc(scoreDoc.doc);
            String content = document.get("fileContent");
            if (content!=null){
                TokenStream tokenStream = analyzer.tokenStream("fileContent", new StringReader(content));
                String highLight = highlighter.getBestFragment(tokenStream, content);
                list.add(highLight);
            }
        }
        reader.close();
        return list;
    }
}
