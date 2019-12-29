package com.etc.newmoudle.Controller;


import com.etc.newmoudle.Bean.ConstantInPro;
import com.etc.newmoudle.Service.SearchService;
import com.etc.newmoudle.Utils.ResponseResult;
import com.etc.newmoudle.VO.OutputTest;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.file.Paths;
import java.util.List;
import java.io.File;

@Controller
@RequestMapping("/Search")
public class SearchController {

    @Autowired
    SearchService searchService;

    @Autowired
    ConstantInPro constantInPro;

    @RequestMapping("/SearchMethods")
    public String SearchMethods(Model model,HttpServletRequest request, HttpServletResponse response) throws Exception {

        String indexPath = constantInPro.getPath();

        System.out.println("索引路径："+indexPath);

        //进入目录查看是否有文件

        File file = new File(indexPath);
        File[] listFiles = file.listFiles();
        if(listFiles == null){
            return "errorHtml";
        }

        List<OutputTest> outputTests = null;
        String SearchText = request.getParameter("searchText");

        System.out.println("搜索关键字："+SearchText);

        //去掉左右两端空格
        String subStr = SearchText.trim();

        outputTests = searchService.testOperator(subStr);

        System.out.println("搜索结果"+outputTests);

        model.addAttribute("searchText",SearchText);
        model.addAttribute("outputTests", outputTests);
        return "result";
    }
}
