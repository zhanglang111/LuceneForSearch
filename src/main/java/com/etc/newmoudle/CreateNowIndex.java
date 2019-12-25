package com.etc.newmoudle;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

import java.io.File;
import java.io.StringReader;
import java.nio.file.Path;
import java.nio.file.Paths;

//import org.apache.lucene.queryparser.surround.parser.QueryParser;

public class CreateNowIndex {

    public static void main(String[] args) throws Exception {
        //第一步，创建一个indexWriter
//第一个参数是索引文件的位置，第二个是索引文件的配置
        String indexPath = "D:/lucene_test/index1";
        String docPath = "D:/lucene_test/docxs";

        Path doc = Paths.get(docPath);

        Directory directory = FSDirectory.open(Paths.get(indexPath));

//        SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer(Version.LUCENE_6_0_0,true);

        Analyzer analyzer = new StandardAnalyzer();

        IndexWriter indexWriter = new IndexWriter(directory,new IndexWriterConfig(analyzer));

        //第二步，索引文

        File file = new File(docPath);

        File[] files = file.listFiles();

        for (File f:files) {
            Document document = new Document();

            String file_name = f.getName();
            Field fileNameField = new TextField("fileName", file_name, Field.Store.YES);

            String path = f.getPath();
            Field filePathField = new StoredField("filePath",path);
//
//            long file_size = FileUtils.sizeOf(f);
//            Field fileSizeField = new LongField("fileSize", file_siz);

            String file_content = org.apache.commons.io.FileUtils.readFileToString(f,"utf-8");

            Field fileContentField = new TextField("fileContent", file_content, Field.Store.YES);

            document.add(fileNameField);
//            document.add(fileSizeField);
            document.add(filePathField);
            document.add(fileContentField);

            // 第四步：使用indexwriter对象将document对象写入索引库，
            // 此过程进行索引创建。并将索引和document对象写入索引库

            indexWriter.addDocument(document);
        }

        indexWriter.close();
    }



    //查询索引
    @Test
    public void SearchIndex() throws  Exception{
        // 第一步：创建一个Directory对象，也就是索引库存放的位置。
        String indexPath = "D:/lucene_test/index1";
        Directory directory = FSDirectory.open(Paths.get(indexPath));
        // 第二步：创建一个indexReader对象，需要指定Directory对象。
        IndexReader indexReader = DirectoryReader.open(directory);// 流

        // 第三步：创建一个indexsearcher对象，需要指定IndexReader对象
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        // 第四步：创建一个TermQuery对象，指定查询的域和查询的关键词。
        Query query = new TermQuery(new Term("fileName","test"));
        // 第五步：执行查询。
        TopDocs topDocs =indexSearcher.search(query,3);
        // 第六步：返回查询结果。遍历查询结果并输出。
        ScoreDoc[] scoreDoc = topDocs.scoreDocs;

//        int numTotalHits = Math.toIntExact(topDocs.totalHits);
//        System.out.println(numTotalHits + " total matching documents");

        for (ScoreDoc doc:scoreDoc) {
            // 文件名称
            int docIndex = doc.doc;

            Document document = indexSearcher.doc(docIndex);
            System.out.println(document.get("fileName"));
            // 文件内容
            System.out.println(document.get("fileContent"));
            // 文件路径
            System.out.println(document.get("filePath"));
            System.out.println("=====================");
        }
        indexReader.close();
    }

    // 查询索引
    @Test
    public void testSearch() throws Exception {
        // 第一步：创建一个Directory对象，也就是索引库存放的位置。
        String indexPath = "D:/lucene_test/index1";
        Directory directory = FSDirectory.open(Paths.get(indexPath));
        // 第二步：创建一个indexReader对象，需要指定Directory对象。
        IndexReader indexReader = DirectoryReader.open(directory);// 流
        // 第三步：创建一个indexsearcher对象，需要指定IndexReader对象
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        // 第四步：创建一个TermQuery对象，指定查询的域和查询的关键词。
//        Query query = new TermQuery(new Term("fileContent", "hello"));


        //通配符搜索   栗子：
        //WildcardQuery query = new WildcardQuery(new Term("fileName","*"));

        Term term = new Term("fileName", "test*");
        WildcardQuery wildcardQuery = new WildcardQuery(term);

        // 第五步：执行查询。
        TopDocs topDocs = indexSearcher.search(wildcardQuery, 4);
        // 第六步：返回查询结果。遍历查询结果并输出。
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;// 文档id
        for (ScoreDoc scoreDoc : scoreDocs) {
            Document document = indexSearcher.doc(scoreDoc.doc);
            System.out.println("文档:" + document.get("filePath"));
            System.out.println("相关度:" + scoreDoc.score);
            System.out.println("================================");
        }
        // 第七步：关闭IndexReader对象
        indexReader.close();
    }


    @Test
    public void LuceneWildCardSearcher() throws Exception{

    }

//    @Test
//    public void testMultiFiledQueryParser()throws Exception{
//
//        String indexPath = "D:/lucene_test/index1";
//        Directory directory = FSDirectory.open(Paths.get(indexPath));
//        @SuppressWarnings("deprecation")
//        IndexReader reader = DirectoryReader.open(directory);
//        IndexSearcher indexSearcher = new IndexSearcher(reader);
//
//        //可以指定默认搜索的域是多个
//
//        String[] fields = {"fileName","fileContent"};
//
//        //创建一个MulitFiledQueryParser对象
//
//        MultiFieldQueryParser queryParser = new MultiFieldQueryParser(fields,new IKAnalyzer());
//
//        Query query = queryParser.parse("java and apache");
//
//        System.out.println(query);
//
//
//        //执行查询
//    }


    //高亮显示
    @Test
    public void search()throws Exception{
        String indexPath = "D:/lucene_test/index1";
        Directory directory = FSDirectory.open(Paths.get(indexPath));
        @SuppressWarnings("deprecation")
        IndexReader reader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(reader);

        // 搜索条件
        Analyzer analyzer = new StandardAnalyzer();

        QueryParser parser = new QueryParser("title", analyzer);

        Query query = parser.parse("如何设计一个电商网站");
        // 搜索结果
        TopDocs hits = searcher.search(query, 10);
        System.out.println(hits.scoreDocs.length);

        // 高亮显示
        /**
　　　　　　　* 创建QueryScorer
　　　　　　　* Fragmenter输出的是文本片段序列，
　　　　　　　* 而Highlighter必须从中挑选出最适合的一个或多个片段呈现给客户，
　　　　　　　* 为了做到这点，Highlighter会要求Java接口Scorer来对每个片段进行评分
　　　　　　　* QueryTermScorer 基于片段中对应Query的项数进行评分
　　　　　　　* QueryScorer只对促成文档匹配的实际项进行评分
　　　　　　　*/
        QueryScorer qs = new QueryScorer(query);
        /** 自定义标注高亮文本标签 */
        SimpleHTMLFormatter sh = new SimpleHTMLFormatter("<span style='color:red;'>", "</span>");
        /**
　　　　　　　* 创建Fragmenter 作用是将原始字符串拆分成独立的片段
　　　　　　　* SimpleSpanFragmenter 是尝试将让片段永远包含跨度匹配的文档
　　　　　　　* SimpleFragmenter 是负责将文本拆分封固定字符长度的片段，但它并处理子边界（默认100）
　　　　　　　* NullFragmenter 整个字符串作为单个片段返回，这适合于处理title域和前台文本较短的域
　　　　　　　*/
        Fragmenter fragmenter = new SimpleSpanFragmenter(qs);
        Highlighter highlighter = new Highlighter(sh, qs);
        highlighter.setTextFragmenter(fragmenter);

        // 获取搜索结果
        for (ScoreDoc scoreDoc : hits.scoreDocs) {
            Document doc = searcher.doc(scoreDoc.doc);
            String title = doc.get("title");
            if (title != null) {
                TokenStream ts = analyzer.tokenStream("title", new StringReader(title));
                String val = highlighter.getBestFragment(ts, title);
                System.out.println(doc.get("id") + " : " + val);
            }
        }
    }
}


