package com.cookandroid.blahblar;

public class VideoLearnVO {
    //id TEXT PRIMARY KEY, name TEXT, passwd TEXT, phone TEXT);
    private String id;
    private String time;
    private String title;

    public VideoLearnVO() {
        this.id = id;
        this.time = time;
        this.title=title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
