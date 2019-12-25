package com.etc.newmoudle.Utils;


import com.etc.newmoudle.VO.OutputTest;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class TopDocsUtil {

    public ArrayList<OutputTest> getList(IndexReader reader, IndexSearcher indexSearcher, Query query) throws Exception{

        System.out.println("将要搜索"+query);

        TopDocs topDocs = indexSearcher.search(query, 10);

        ScoreDoc scoreDocs[] = topDocs.scoreDocs;

        ArrayList<OutputTest> outputTests = new ArrayList<>();
        for (ScoreDoc scoreDoc : scoreDocs) {
            Document doc = indexSearcher.doc(scoreDoc.doc);
            OutputTest outputTest = new OutputTest(doc.get("filePath"),scoreDoc.score,doc.get("fileName"),doc.get("fileContent"));
            outputTests.add(outputTest);
        }
        reader.close();
        return outputTests;
    }
}
