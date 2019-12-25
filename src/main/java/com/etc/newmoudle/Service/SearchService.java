package com.etc.newmoudle.Service;


import com.etc.newmoudle.VO.OutputTest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SearchService {

    public List<OutputTest> CommonSearch(String SearchText) throws Exception;


    public void InspecificDomainSearch();

    public List<String> WildcardSearch(String SearchText) throws Exception;


    public List<OutputTest> WildcardSearch2(String SearchText) throws Exception;


    public List<OutputTest> BooleanSearch(String[] strings) throws Exception;
}
