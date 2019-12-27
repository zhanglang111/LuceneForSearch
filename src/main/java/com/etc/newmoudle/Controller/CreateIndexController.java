package com.etc.newmoudle.Controller;


import com.etc.newmoudle.Bean.ConstantInPro;
import com.etc.newmoudle.Bean.MyCommandLineRunner;
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
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("up")
public class CreateIndexController {

    @Autowired
    ConstantInPro constantInPro;

    @RequestMapping(value = "/createIndex",method = RequestMethod.POST)
    public String CreateIndex(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception{
        //跳到另外一个controller

        String docpath = request.getParameter("docPath");//value是可以修改的
        String fileName = request.getParameter("fileName");
        String filePath = request.getParameter("filePath");
        String fileContent = request.getParameter("fileContent");
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

        for (File f:files) {
            Document document = new Document();
            String file_name = f.getName();
            Field fileNameField = new TextField(fileName, file_name, Field.Store.YES);
            String path = f.getPath();
            Field filePathField = new StoredField(filePath,path);
            String file_content = org.apache.commons.io.FileUtils.readFileToString(f,"utf-8");
            Field fileContentField = new TextField(fileContent, file_content, Field.Store.YES);
            document.add(fileNameField);
            document.add(filePathField);
            document.add(fileContentField);
            indexWriter.addDocument(document);
        }

        indexWriter.close();

        return "FileUploadSuccess";

//        return
    }
}
