package com.cookandroid.blahblar;

import android.text.format.DateUtils;

import java.sql.Date;

public class ReviewVO {
    //id TEXT PRIMARY KEY, name TEXT, passwd TEXT, phone TEXT);
    private String no;
    private String id;
    private String title;
    private String todaytime;
    private String wsent;

    public ReviewVO() {
        this.no=no;
        this.id = id;
        this.title= title;
        this.todaytime= todaytime;
        this.wsent = wsent;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTodaytime() {
        return todaytime;
    }

    public void setTodaytime(String todaytime) {
        this.todaytime = todaytime;
    }

    public String getWsent() {
        return wsent;
    }

    public void setWsent(String wsent) {
        this.wsent = wsent;
    }
}

