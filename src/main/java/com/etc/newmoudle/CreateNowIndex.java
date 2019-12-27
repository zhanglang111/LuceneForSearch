//package com.etc.newmoudle;
//
//import org.apache.lucene.analysis.Analyzer;
//import org.apache.lucene.analysis.TokenStream;
//import org.apache.lucene.analysis.standard.StandardAnalyzer;
//import org.apache.lucene.document.Document;
//import org.apache.lucene.document.Field;
//import org.apache.lucene.document.StoredField;
//import org.apache.lucene.document.TextField;
//import org.apache.lucene.index.*;
//import org.apache.lucene.queryparser.classic.QueryParser;
//import org.apache.lucene.search.*;
//import org.apache.lucene.search.highlight.*;
//import org.apache.lucene.store.Directory;
//import org.apache.lucene.store.FSDirectory;
//import org.junit.Test;
//
//import java.io.File;
//import java.io.StringReader;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
////import org.apache.lucene.queryparser.surround.parser.QueryParser;
//
//public class CreateNowIndex {
//
//    public static void main(String[] args) throws Exception {
//        //第一步，创建一个indexWriter
////第一个参数是索引文件的位置，第二个是索引文件的配置
//        String indexPath = "D:/lucene_test/index1";
//        String docPath = "D:/lucene_test/docxs";
//
//        Path doc = Paths.get(docPath);
//
//        Directory directory = FSDirectory.open(Paths.get(indexPath));
//
////        SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer(Version.LUCENE_6_0_0,true);
//
//        Analyzer analyzer = new StandardAnalyzer();
//
//        IndexWriter indexWriter = new IndexWriter(directory,new IndexWriterConfig(analyzer));
//
//        //第二步，索引文
//
//        File file = new File(docPath);
//
//        File[] files = file.listFiles();
//
//        for (File f:files) {
//            Document document = new Document();
//            String file_name = f.getName();
//            Field fileNameField = new TextField("fileName", file_name, Field.Store.YES);
//            String path = f.getPath();
//            Field filePathField = new StoredField("filePath",path);
//            String file_content = org.apache.commons.io.FileUtils.readFileToString(f,"utf-8");
//            Field fileContentField = new TextField("fileContent", file_content, Field.Store.YES);
//            document.add(fileNameField);
//            document.add(filePathField);
//            document.add(fileContentField);
//            indexWriter.addDocument(document);
//        }
//
//        indexWriter.close();
//    }
//}
//
//
