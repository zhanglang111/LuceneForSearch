package com.etc.newmoudle.Service;


import com.etc.newmoudle.VO.OutputTest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SearchService {

    public List<OutputTest> testOperator(String strings) throws Exception;

}
