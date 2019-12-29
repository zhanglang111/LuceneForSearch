package com.etc.newmoudle.Controller;


import com.etc.newmoudle.Service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

@Controller
@RequestMapping("upload")
public class UploadFileToServer {

    @Autowired
    UploadService uploadService;

    @RequestMapping("uploadFile")
    public String uploadFile(@RequestParam("files") File[] files, HttpServletRequest request){

        uploadService.uploadFileToServer(files);

        //使用socket将文件上传到

        return "FileUploadSuccess";
    }
}
