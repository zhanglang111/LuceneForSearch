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
    public String SearchMethods(Model model,HttpServletRequest request, HttpServletResponse response) throws Exception{

        List<OutputTest> outputTests = null;
        String SearchText = request.getParameter("searchText");
        //去掉左右两端空格
        String subStr =  SearchText.trim();
        if (subStr.contains(" ")){
            /**
             * 多词联合搜索
             */
            String[] strings = subStr.split(" ");
            outputTests = searchService.BooleanSearch(strings);
            System.out.println(outputTests);
        }else if(subStr.contains("*")||subStr.contains("?")){
            /**
             * 通配符搜索
             */
            outputTests = searchService.WildcardSearch2(subStr);
        }else{
            /**
             * 普通搜索
             */
            outputTests = searchService.CommonSearch(subStr);
        }
        model.addAttribute("outputTests",outputTests);
        return "result";
    }

    @RequestMapping("/high")
    public String high(Model model, String SearchText) throws Exception{
        List<String> list = searchService.WildcardSearch(SearchText);
        System.out.println(list);
        model.addAttribute("list",list);
        return "out";
    }


    @ResponseBody
    @RequestMapping("/InspecificDomainSearch.do")
    public ResponseResult InspecificDomainSearch(HttpServletRequest request, HttpServletResponse response) {
        searchService.InspecificDomainSearch();
        return ResponseResult.ok(1);
    }
}
