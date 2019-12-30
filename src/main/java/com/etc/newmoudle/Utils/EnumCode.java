package com.etc.newmoudle.Utils;

public enum EnumCode {

    GB2312(1,"GB2312"),
    ISO_8859_1(2,"ISO-8859-1"),
    GBK(3,"GBK"),
    ANSI(4,"ANSI");

    private long value;
    private String label;

    EnumCode(int value,String label) {
        this.value = value;
        this.label = label;
    }
}
