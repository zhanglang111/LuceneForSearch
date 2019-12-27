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

    //QueryParser是万能搜索！！！！
    public List<OutputTest> testOperator(String SearchText) throws Exception {

        Analyzer analyzer = new StandardAnalyzer();
        Query query;
        QueryParser parser = new QueryParser("fileContent", analyzer);
        query = parser.parse(SearchText);
        return topDocsUtil.higtlight(myCommandLineRunner.reader,myCommandLineRunner.indexSearcher,query);
    }
}
