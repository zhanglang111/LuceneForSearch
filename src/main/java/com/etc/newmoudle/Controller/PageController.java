package com.etc.newmoudle.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
    @RequestMapping("/index")
    //里面并没有传任何的参数
    public String index(){
        return "indexHtml";
    }


    @RequestMapping("/createFileIndex")
    public String createFileIndex(){
        return "Selectfeild";
    }
}
