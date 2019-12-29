package com.etc.newmoudle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class NewmoudleApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewmoudleApplication.class, args);
    }

}
