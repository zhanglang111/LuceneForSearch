package com.etc.newmoudle.Controller;

import java.io.File;


import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Controller
public class FileUploadController {

    @RequestMapping("douploadRemote")
    public String douploadRemote(HttpServletRequest request, @RequestParam("file") MultipartFile multipartFile) throws Exception{

        if (multipartFile.isEmpty()) {
            return "file is empty.";
        }
        String DocPath = ResourceUtils.getURL("classpath:").getPath();

        System.out.println(DocPath);
        return "FileUploadSuccess";
    }
}
