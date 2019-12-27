package com.etc.newmoudle.Bean;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;

@Component
public class MyCommandLineRunner implements CommandLineRunner {

    @Autowired
    ConstantInPro constantInPro;


    public ConstantInPro getConstantInPro() {
        return constantInPro;
    }

    public static IndexSearcher indexSearcher = null;


    public static IndexReader reader  = null;

    @Override
    public void run(String... var1) throws Exception{
        //这里面写东西
        String indexPath = constantInPro.getPath();

        System.out.println(indexPath);

        Directory directory = FSDirectory.open(Paths.get(indexPath));
        reader = DirectoryReader.open(directory);
        indexSearcher = new IndexSearcher(reader);

    }
}
