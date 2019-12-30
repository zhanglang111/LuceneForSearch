package com.etc.newmoudle.Controller;


import com.etc.newmoudle.Bean.ConstantInPro;
import com.etc.newmoudle.Bean.IndexSearchConfig;
import com.etc.newmoudle.Utils.UTF8Util;
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
import org.apache.poi.Version;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("indexFile")
public class IndexController {

    @Autowired
    ConstantInPro constantInPro;

    @Autowired
    UTF8Util utf8Util;

//    @RequestMapping(value = "/createIndex",method = RequestMethod.POST)
//    public String CreateIndex(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception{
//        //跳到另外一个controller
//
//        //修改文件编码格式
//
//        //
//        String docpath = request.getParameter("docPath");//value是可以修改的
//        String fileName = request.getParameter("fileName");
//        String filePath = request.getParameter("filePath");
//        String fileContent = request.getParameter("fileContent");
//
//        System.out.println(docpath);
//
////        String fileScore = request.getParameter("fileScore");
//
//        Path doc = Paths.get(docpath);
//        //获取默认路径
//        Directory directory = FSDirectory.open(Paths.get(constantInPro.getPath()));
//
//
////        SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer(Version.LUCENE_6_0_0,true);
//
//        Analyzer analyzer = new StandardAnalyzer();
//
//        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
//
//        //更新模式
//        indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
//
//        IndexWriter indexWriter = new IndexWriter(directory,new IndexWriterConfig(analyzer));
//
//
//
//        //第二步，索引文
//
//        File file = new File(docpath);
//
//        File[] files = file.listFiles();
//
//        for (File f:files) {
//            Document document = new Document();
//
//            if(fileName!=null){
//                String file_name = f.getName();
//                Field fileNameField = new TextField(fileName, file_name, Field.Store.YES);
//                document.add(fileNameField);
//            }
//
//           if(filePath!=null){
//               String file_path = f.getName();
//               Field filePathField = new TextField(filePath, file_path, Field.Store.YES);
//               document.add(filePathField);
//           }
//
//
//            String file_content = org.apache.commons.io.FileUtils.readFileToString(f,"utf-8");
//            if(fileContent!=null){
//                Field fileContentField = new TextField(fileContent, file_content, Field.Store.YES);
//                document.add(fileContentField);
//            }
//            indexWriter.addDocument(document);
//        }
//
//        indexWriter.close();
//        return "FileUploadSuccess";
//    }


//    @RequestMapping("/CreateIndex")
//    public String CreateIndex(@RequestParam("files") File[] files, HttpServletRequest request) throws  Exception{
//
//
//        System.out.println(files.length);
//
//        Directory directory = FSDirectory.open(Paths.get(constantInPro.getPath()));
//
//
////        SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer(Version.LUCENE_6_0_0,true);
//
//        Analyzer analyzer = new StandardAnalyzer();
//
//        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
//
//        indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
//
//        IndexWriter indexWriter = new IndexWriter(directory,indexWriterConfig);
//
//        for (File f:files) {
//            System.out.println(f.getPath());
//            System.out.println(f.getName()+f.toString()+f.getPath()+f.getAbsolutePath()+f.getCanonicalPath()+f.isFile()+f.length());
//            Document document = new Document();
//            Field fileNameField = new TextField("fileName", f.getName(), Field.Store.YES);
//
//            System.out.println(fileNameField);
//
//            Field filePathField = new TextField("filePath", f.getPath(), Field.Store.YES);
//
//            System.out.println(filePathField);
//
//            //看来还是要保存在服务器，然后再说？
//
//            String file_content = org.apache.commons.io.FileUtils.readFileToString(f,"utf-8");
//            Field fileContentField = new TextField("fileContent", file_content, Field.Store.YES);
//
//            document.add(fileNameField);
//            document.add(filePathField);
//            document.add(fileContentField);
//
//            indexWriter.addDocument(document);
//        }
//        indexWriter.close();
//        return "FileUploadSuccess";
//    }



    @RequestMapping("/CreateIndex")
    public String CreateIndex(@RequestParam("files") MultipartFile[] files, HttpServletRequest request) throws  Exception{
        String rect = (String) request.getAttribute("rect");
        System.out.println(rect);


        Directory directory = FSDirectory.open(Paths.get(constantInPro.getPath()));


//        SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer(Version.LUCENE_6_0_0,true);

        Analyzer analyzer = new StandardAnalyzer();

        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);

        indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);

        IndexWriter indexWriter = new IndexWriter(directory,indexWriterConfig);

        for (MultipartFile f:files) {
            Document document = new Document();
            Field fileNameField = new TextField("fileName", f.getName(), Field.Store.YES);
            Field filePathField = new TextField("filePath", f.getOriginalFilename(), Field.Store.YES);

            InputStream inputStream =  f.getInputStream();

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"GBK");

            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String file_content = "";

            String temp = null;

            while ((temp = bufferedReader.readLine())!=null){
                file_content += temp;
            }

//            String file_content = utf8Util.toUTF8(Result);

            System.out.println(file_content);

//            String file_content = org.apache.commons.io.FileUtils.readFileToString(f,"utf-8");
            Field fileContentField = new TextField("fileContent", file_content, Field.Store.YES);

            document.add(fileNameField);
            document.add(filePathField);
            document.add(fileContentField);

            indexWriter.addDocument(document);
        }
        indexWriter.close();
        return "FileUploadSuccess";
    }


//    @RequestMapping("/AddIndex")
//    public String AddIndex()throws Exception {
//
//        return "AddIndexSuccess";
//    }

//    @RequestMapping("Create2")
//    public String Create2(@RequestParam("files") File[] files, HttpServletRequest request) throws Exception{
//        Directory directory = FSDirectory.open(Paths.get(constantInPro.getPath()));
//        Analyzer analyzer = new StandardAnalyzer();
//        IndexWriter indexWriter = new IndexWriter(directory, new IndexWriterConfig(analyzer));
//
//        for (int i = 0; i < files.length; i++) {
//            // 文件是第几个
//            System.out.println("这是第" + i + "个文件----------------");
//            // 文件的完整路径
//            System.out.println("完整路径：" + files[i].toString());
//            // 获取文件名称
//            String fileName = files[i].getName();
//            // 获取文件后缀名，将其作为文件类型
//            String fileType = fileName.substring(fileName.lastIndexOf(".") + 1,
//                    fileName.length()).toLowerCase();
//            // 文件名称
//            System.out.println("文件名称：" + fileName);
//            // 文件类型
//            System.out.println("文件类型：" + fileType);
//
//            Document doc = new Document();
//
//            InputStream in = new FileInputStream(files[i]);
//            InputStreamReader reader = null;
//
//            if (fileType != null && !fileType.equals("")) {
//
//                if (fileType.equals("doc")) {
//                    // 获取doc的word文档
//                    WordExtractor wordExtractor = new WordExtractor(in);
//                    Field fielddoc = new TextField("fileContent",wordExtractor.getText(),Field.Store.YES);
//                    doc.add(fielddoc);
//                    indexWriter.addDocument(doc);
//                    // 关闭文档
//                    wordExtractor.close();
//                    System.out.println("注意：已为文件“" + fileName + "”创建了索引");
//                } else if (fileType.equals("docx")) {
//                    // 获取docx的word文档
//                    XWPFWordExtractor xwpfWordExtractor = new XWPFWordExtractor(
//                            new XWPFDocument(in));
//                    Field fielddocx = new TextField("fileContent",xwpfWordExtractor.getText(),Field.Store.YES);
//                    doc.add(fielddocx);
//                    indexWriter.addDocument(doc);
//                    // 关闭文档
//                    xwpfWordExtractor.close();
//                    System.out.println("注意：已为文件“" + fileName + "”创建了索引");
//
//                }
////                else if (fileType.equals("pdf")) {
////                    // 获取pdf文档
////                    PDFParser parser = new PDFParser((RandomAccessRead) in);
////                    parser.parse();
////                    PDDocument pdDocument = parser.getPDDocument();
//////                    PDFTextStripper stripper = new PDFTextStripper();
//////                    // 创建Field对象，并放入doc对象中
//////                    doc.add(new Field("contents", stripper.getText(pdDocument),
//////                            Field.Store.NO, Field.Index.ANALYZED));
//////                    // 关闭文档
//////                    pdDocument.close();
//////                    System.out.println("注意：已为文件“" + fileName + "”创建了索引");
////
////                }
//                else if (fileType.equals("txt")) {
//                    // 建立一个输入流对象reader
//                    reader = new InputStreamReader(in);
//                    // 建立一个对象，它把文件内容转成计算机能读懂的语言
//                    BufferedReader br = new BufferedReader(reader);
//                    String txtFile = "";
//                    String line = null;
//
//                    while ((line = br.readLine()) != null) {
//                        // 一次读入一行数据
//                        txtFile += line;
//                    }
//                    Field fieldtxt = new TextField("fileContent",txtFile,Field.Store.YES);
//                    doc.add(fieldtxt);
//                    indexWriter.addDocument(doc);
//                    System.out.println("注意：已为文件“" + fileName + "”创建了索引");
//                } else {
//                    System.out.println();
//                    continue;
//                }
//            }
//        }
//        indexWriter.close();
//        return "FileUploadSuccess";
//    }
//
//    @RequestMapping("/AddIndex")
//    public String AddIndex()throws Exception {
//
//        return "AddIndexSuccess";
//    }


}
