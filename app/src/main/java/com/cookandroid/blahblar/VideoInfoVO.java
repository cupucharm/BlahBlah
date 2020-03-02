package com.cookandroid.blahblar;

public class VideoInfoVO {
    //id TEXT PRIMARY KEY, name TEXT, passwd TEXT, phone TEXT);
    private String title;
    private String tag;
    private int level;
    private String runtime;

    public VideoInfoVO() {
        this.title = title;
        this.tag = tag;
        this.level = level;
    }

    public String getTitle() {
        return title;
    }

    public void setId(String title) {
        this.title = title;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }
}