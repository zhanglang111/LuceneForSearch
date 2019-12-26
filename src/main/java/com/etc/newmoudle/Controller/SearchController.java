package com.etc.newmoudle.Controller;


import com.etc.newmoudle.Service.SearchService;
import com.etc.newmoudle.Utils.ResponseResult;
import com.etc.newmoudle.VO.OutputTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/Search")
public class SearchController {

    @Autowired
    SearchService searchService;

    @RequestMapping("/SearchMethods")
    public String SearchMethods(Model model,HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<OutputTest> outputTests = null;
        String SearchText = request.getParameter("searchText");
        //去掉左右两端空格
        String subStr = SearchText.trim();

        outputTests = searchService.testOperator(subStr);

        model.addAttribute("searchText",SearchText);
        model.addAttribute("outputTests", outputTests);
        return "result";
    }

//    @RequestMapping("/SearchMethodsResult")
//    public String SearchMethodsResult(Model model,HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//        List<OutputTest> outputTests = null;
//        String SearchText = request.getParameter("searchText");
//        //去掉左右两端空格
//        String subStr = SearchText.trim();
//
//        outputTests = searchService.testOperator(subStr);
//        model.addAttribute("outputTests", outputTests);
//        System.out.println(outputTests);
//        return "result";
//    }
}
