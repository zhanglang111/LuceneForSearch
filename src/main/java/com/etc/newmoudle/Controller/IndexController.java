package com.etc.newmoudle.Controller;


import com.etc.newmoudle.Bean.ConstantInPro;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("indexFile")
public class IndexController {

    @Autowired
    ConstantInPro constantInPro;

    @RequestMapping(value = "/createIndex",method = RequestMethod.POST)
    public String CreateIndex(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        //跳到另外一个controller

        //修改文件编码格式

        String docpath = request.getParameter("docPath");//value是可以修改的
        String fileName = request.getParameter("fileName");
        String filePath = request.getParameter("filePath");
        String fileContent = request.getParameter("fileContent");

        System.out.println(docpath);

//        String fileScore = request.getParameter("fileScore");

        Path doc = Paths.get(docpath);
        //获取默认路径
        Directory directory = FSDirectory.open(Paths.get(constantInPro.getPath()));


//        SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer(Version.LUCENE_6_0_0,true);

        Analyzer analyzer = new StandardAnalyzer();

        IndexWriter indexWriter = new IndexWriter(directory,new IndexWriterConfig(analyzer));

        //第二步，索引文

        File file = new File(docpath);

        File[] files = file.listFiles();

        //这里需要特殊照顾下

        for (File f:files) {
            Document document = new Document();

            if(fileName!=null){
                String file_name = f.getName();
                Field fileNameField = new TextField(fileName, file_name, Field.Store.YES);
                document.add(fileNameField);
            }

           if(filePath!=null){
               String file_path = f.getName();
               Field filePathField = new TextField(filePath, file_path, Field.Store.YES);
               document.add(filePathField);
           }


            String file_content = org.apache.commons.io.FileUtils.readFileToString(f,"utf-8");
            if(fileContent!=null){
                Field fileContentField = new TextField(fileContent, file_content, Field.Store.YES);
                document.add(fileContentField);
            }
            indexWriter.addDocument(document);
        }

        indexWriter.close();
        return "FileUploadSuccess";
    }


    @RequestMapping("/AddIndex")
    public String AddIndex()throws Exception {

        return "AddIndexSuccess";
    }
}
