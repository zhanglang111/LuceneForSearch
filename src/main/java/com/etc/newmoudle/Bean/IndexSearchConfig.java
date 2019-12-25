package com.etc.newmoudle.Bean;


import org.apache.lucene.search.IndexSearcher;
import org.springframework.stereotype.Component;

@Component
public class IndexSearchConfig {

    private IndexSearcher indexSearcher;

    public IndexSearcher getIndexSearcher() {
        return indexSearcher;
    }

    public void setIndexSearcher(IndexSearcher indexSearcher) {
        this.indexSearcher = indexSearcher;
    }

    //定义全局变量
    private static IndexSearchConfig indexSearchConfig;

    //私有化构造函数
    private IndexSearchConfig(){
        this.indexSearcher = indexSearcher;
    }


    public static IndexSearchConfig getInstance(){
        if(indexSearchConfig==null){
            //加上同步锁，保证线程安全
            synchronized(IndexSearchConfig.class){
                indexSearchConfig = new IndexSearchConfig();
            }
        }
        return indexSearchConfig;
    }
}


