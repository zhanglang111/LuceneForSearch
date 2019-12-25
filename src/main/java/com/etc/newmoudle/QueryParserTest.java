//package com.etc.newmoudle;
//
//import org.apache.lucene.analysis.Analyzer;
//import org.apache.lucene.analysis.standard.StandardAnalyzer;
//import org.apache.lucene.document.Document;
//import org.apache.lucene.document.Field;
//import org.apache.lucene.index.CorruptIndexException;
//import org.apache.lucene.index.IndexWriter;
//import org.apache.lucene.queryparser.classic.QueryParser;
//import org.apache.lucene.search.IndexSearcher;
//import org.apache.lucene.search.Query;
//import org.apache.lucene.store.Directory;
//import org.apache.lucene.store.FSDirectory;
//import org.apache.lucene.store.LockObtainFailedException;
//
//
//
//import org.apache.lucene.analysis.standard.StandardAnalyzer;
//import org.apache.lucene.document.Document;
//import org.apache.lucene.document.Field;
//import org.apache.lucene.index.CorruptIndexException;
//import org.apache.lucene.index.IndexWriter;
//import org.apache.lucene.queryParser.ParseException;
//import org.apache.lucene.search.Hits;
//import org.apache.lucene.search.IndexSearcher;
//import org.apache.lucene.search.Query;
//import org.apache.lucene.store.LockObtainFailedException;
//
//import java.io.IOException;
//import java.nio.file.Paths;
//import java.util.Date;
//
//public class QueryParserTest {
//
//    String path = "D:/lucene_test/index1";
//
//    public void createIndex(){
//        IndexWriter writer;
//        try {
//            writer = new IndexWriter(path,new StandardAnalyzer(),true);
//
//            String valueA = "在新安全策略中配置的设置可能导致应用程序或服务出现兼容性问题。因此，在将新的安全策略应用于生产服务器之前，应对其进行全面的测试。";
//            Field fieldA1 = new Field("contents",valueA,Field.Store.YES,Field.Index.TOKENIZED);
//            Field fieldA2 = new Field("date","2008",Field.Store.YES,Field.Index.UN_TOKENIZED);
//            Document docA = new Document();
//            docA.add(fieldA1);
//            docA.add(fieldA2);
//
//            String valueB = "强烈建议您确保用于创建安全策略的原型计算机与要在服务级进行配置的目标服务器相匹配。";
//            Field fieldB1 = new Field("contents",valueB,Field.Store.YES,Field.Index.TOKENIZED);
//            Field fieldB2 = new Field("date","2002",Field.Store.YES,Field.Index.UN_TOKENIZED);
//            Document docB = new Document();
//            docB.add(fieldB1);
//            docB.add(fieldB2);
//
//            String valueC = "通过创建专为服务器的特定角色而设计的安全策略，SCW 有助于减小服务器的受攻击面。管理员可以通过识别执行相同或类似任务的服务器组来简化策略的创建和分发。";
//            Field fieldC1 = new Field("contents",valueC,Field.Store.YES,Field.Index.TOKENIZED);
//            Field fieldC2 = new Field("date","2006",Field.Store.YES,Field.Index.UN_TOKENIZED);
//            Document docC = new Document();
//            docC.add(fieldC1);
//            docC.add(fieldC2);
//
//            writer.addDocument(docA);
//            writer.addDocument(docB);
//            writer.addDocument(docC);
//            writer.close();
//        } catch (CorruptIndexException e) {
//            e.printStackTrace();
//        } catch (LockObtainFailedException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void main(String[] args) throws Exception{
//        QueryParserTest queryParserTest = new QueryParserTest();
//        queryParserTest.createIndex();    // 调用createIndex()方法建立索引
//        try {
//            String userKeyword = "服务器 date:2006";
//            QueryParser queryParser = new QueryParser("contents",new ThesaurusAnalyzer());
//            //QueryParser queryParser = new QueryParser("contents",new StandardAnalyzer());
//            queryParser.setDefaultOperator(QueryParser.AND_OPERATOR);    // 使用了AND_OPERATOR
//            Query query = queryParser.parse(userKeyword);
//            System.out.println("解析用户输入关键字 ： "+query.toString());
//            IndexSearcher searcher = new IndexSearcher(queryParserTest.path);
//            Date startTime = new Date();
//            Hits hits = searcher.search(query);
//            System.out.println("********************************************************************");
//            for(int i=0;i<hits.length();i++){
//                System.out.println("Document的内部编号为 ： "+hits.id(i));
//                System.out.println("Document内容为 ： "+hits.doc(i));
//                System.out.println("Document的得分为 ： "+hits.score(i));
//            }
//            System.out.println("********************************************************************");
//            System.out.println("共检索出符合条件的Document "+hits.length()+" 个。");
//            Date finishTime = new Date();
//            long timeOfSearch = finishTime.getTime() - startTime.getTime();
//            System.out.println("本次搜索所用的时间为 "+timeOfSearch+" ms");
//        } catch (ParseException e) {
//            e.printStackTrace();
//        } catch (CorruptIndexException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//}