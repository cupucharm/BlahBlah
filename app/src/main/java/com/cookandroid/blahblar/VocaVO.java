package com.cookandroid.blahblar;

public class VocaVO {
    //id TEXT PRIMARY KEY, name TEXT, passwd TEXT, phone TEXT);
    private String id;
    private String word;
    private String mean;

    public VocaVO() {
        this.id = id;
        this.word = word;
        this.mean = mean;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }

}
