package com.etc.newmoudle.Controller;

import java.io.File;
import java.io.IOException;


import ch.qos.logback.core.util.FileUtil;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Controller

@RequestMapping("/upload")
public class FileUploadController {

    @RequestMapping("douploadRemote")
    public String douploadRemote(HttpServletRequest request, @RequestParam("dir") MultipartFile[] dir) throws Exception{


//        System.out.println(rootPath);

//        System.out.println("上传文件夹...");
//        File file;
//        String fileName="";
//        String filePath="";
//        for (MultipartFile f : dir) {
//            fileName=f.getOriginalFilename();
//            String type=f.getContentType();
//            System.out.println("\n"+fileName+" ,"+type);
//            filePath="D:\\upload\\"+fileName.substring(0,fileName.lastIndexOf("/"));
//
//            System.out.println(filePath);
//
//            file = new File("D:\\upload\\" + fileName);
//            try {
//                file.createNewFile();
//                //将上传文件保存到一个目标文件中
//                f.transferTo(file);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        return "FileUploadSuccess";
    }
}
