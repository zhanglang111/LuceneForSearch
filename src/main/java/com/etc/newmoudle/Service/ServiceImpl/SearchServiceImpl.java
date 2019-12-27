package com.etc.newmoudle.Service.ServiceImpl;

import com.etc.newmoudle.Bean.ConstantInPro;
import com.etc.newmoudle.Service.SearchService;
import com.etc.newmoudle.Utils.TopDocsUtil;
import com.etc.newmoudle.VO.OutputTest;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;
import java.util.List;


@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    ConstantInPro constantInPro;

    @Autowired
    TopDocsUtil topDocsUtil;

    //QueryParser是万能搜索！！！！
    public List<OutputTest> testOperator(String SearchText) throws Exception {


        String indexPath = constantInPro.getPath();

        System.out.println(indexPath);

        Directory directory = FSDirectory.open(Paths.get(indexPath));
        IndexReader reader = DirectoryReader.open(directory);
        IndexSearcher indexSearcher = new IndexSearcher(reader);

        Analyzer analyzer = new StandardAnalyzer();
        Query query;
        QueryParser parser = new QueryParser("fileContent", analyzer);
        query = parser.parse(SearchText);
        return topDocsUtil.higtlight(reader,indexSearcher,query);
    }
}
