package com.etc.newmoudle;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(String[] args) throws Exception{

        String path = "C:\\Users\\langz\\Desktop\\新建文本文档.txt";

        InputStream in = new FileInputStream(path);

        InputStreamReader inputStreamReader = new InputStreamReader(in,"UTF-8");

        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String Result = "";

        String temp = null;

        while((temp = bufferedReader.readLine())!= null){
            Result += temp;
        }


//        String file_content = new String(Result.getBytes(),"UTF-8");

//        String value = new String(Result.getBytes("ISO-8859-1"),"utf-8");
//        file_content = new String(file_content .getBytes(), "ISO-8859-1");
//filename = URLEncoder.encode(file_name,"UTF-8");
//        response.setHeader("Content-Disposition", "attachment;filename=\"" + filename + "\"");
        System.out.println(Result);
    }



    @org.junit.Test
    public void getChinese() throws Exception{


        ArrayList<String> arrayList = new ArrayList<>();

        String path = "C:\\Users\\langz\\Desktop\\新建文本文档.txt";

//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(path)),"UTF-8");

        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(new FileInputStream(path),"GBK"));

        String line = null;
        while((line = bufferedReader.readLine())!= null){
            arrayList.add(line);
        }

        System.out.println(arrayList);
    }
}
