package com.etc.newmoudle.Utils;


import com.etc.newmoudle.VO.OutputTest;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.*;
import org.springframework.stereotype.Component;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class TopDocsUtil {

    public List<OutputTest> higtlight(IndexReader reader, IndexSearcher indexSearcher, Query query) throws Exception {

        Analyzer analyzer = new StandardAnalyzer();

        System.out.println(query);

        TopDocs topDocs = indexSearcher.search(query, 4);

        SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<b><font color=red>","</font></b>");

        QueryScorer scorer = new QueryScorer(query);
        Fragmenter fragmenter = new SimpleSpanFragmenter(scorer);
        Highlighter highlighter = new Highlighter(simpleHTMLFormatter, scorer);

        highlighter.setTextFragmenter(fragmenter);

        ScoreDoc scoreDocs[] = topDocs.scoreDocs;
        //返回数据

        List<OutputTest> outputTests = new ArrayList<>();

        for (ScoreDoc scoreDoc : scoreDocs) {
            Document doc = indexSearcher.doc(scoreDoc.doc);
            String content = doc.get("fileContent");
            if (content!=null){
                TokenStream tokenStream = analyzer.tokenStream("fileContent", new StringReader(content));
                String highlighterBestFragment = highlighter.getBestFragment(tokenStream, content);

                OutputTest outputTest = new OutputTest(doc.get("filePath"),scoreDoc.score,doc.get("fileName"),highlighterBestFragment);
                outputTests.add(outputTest);
            }
        }
        return outputTests;
    }
}
