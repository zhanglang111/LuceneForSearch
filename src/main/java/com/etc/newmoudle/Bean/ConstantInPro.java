package com.etc.newmoudle.Bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class ConstantInPro {

    @Value("${indexPath}")
    String Path;

    public String getPath() {
        return Path;
    }
}
