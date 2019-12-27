package com.etc.newmoudle.Utils;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 *
 */

/**
 * @author weict
 *
 */
public class ChangeFileCode {
    // 读取的文件
    private String fileIn;

    // 读取時文件用的编码
    private String fileInEn;

    // 写出的文件
    private String fileOut;

    // 写出時文件用的编码
    private String fileOutEn;

    public void setFileIn(String fileInPath, String fileInEncoding) {
        this.setFileIn(fileInPath);
        this.setFileInEn(fileInEncoding);
    }

    public void setFileOut(String fileOutPath, String fileOutEncoding) {
        this.setFileOut(fileOutPath);
        this.setFileOutEn(fileOutEncoding);
    }

    public void start() {
        String str = this.read(fileIn,fileInEn);
        this.write(fileOut, fileOutEn, str);
    }

    /**
     * 读文件
     *
     * @param fileName
     * @param encoding
     */
    private String read(String fileName, String encoding) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    new FileInputStream(fileName), encoding));

            String string = "";
            String str = "";
            while ((str = in.readLine()) != null) {
                string += str + "\n";
            }
            in.close();
            System.out.println(string);
            return string;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

    /**
     * 写文件
     *
     * @param fileName
     *            新的文件名
     * @param encoding
     *            写出的文件的编码方式
     * @param str
     */
    private void write(String fileName, String encoding, String str) {
        try {
            Writer out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(fileName), encoding));
            out.write(str);
            out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String getFileIn() {
        return fileIn;
    }

    public void setFileIn(String fileIn) {
        this.fileIn = fileIn;
    }

    public String getFileInEn() {
        return fileInEn;
    }

    public void setFileInEn(String fileInEn) {
        this.fileInEn = fileInEn;
    }

    public String getFileOut() {
        return fileOut;
    }

    public void setFileOut(String fileOut) {
        this.fileOut = fileOut;
    }

    public String getFileOutEn() {
        return fileOutEn;
    }

    public void setFileOutEn(String fileOutEn) {
        this.fileOutEn = fileOutEn;
    }

}
