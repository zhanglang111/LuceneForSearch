package com.etc.newmoudle.VO;


import lombok.Data;

@Data
public class OutputTest {

    public OutputTest() {
    }

    public OutputTest(String filePath, float fileScore, String fileName, String fileContent) {
        this.filePath = filePath;
        this.fileScore = fileScore;
        this.fileName = fileName;
        this.fileContent = fileContent;
    }

    public OutputTest(String filePath, float fileScore, String fileName, String fileContent, String highLight) {
        this.filePath = filePath;
        this.fileScore = fileScore;
        this.fileName = fileName;
        this.fileContent = fileContent;
        this.highLight = highLight;
    }

    private String filePath;
    private float fileScore;
    private String fileName;
    private String fileContent;
    private String highLight;
}
