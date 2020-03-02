package com.cookandroid.blahblar;

public class RecommExpreVO {
    //id TEXT PRIMARY KEY, name TEXT, passwd TEXT, phone TEXT);
    private String title;
    private String stpoint;
    private String expr;

    public RecommExpreVO() {
        this.title = title;
        this.stpoint = stpoint;
        this.expr = expr;
    }

    public String getTitle() {
        return title;
    }

    public void setId(String title) {
        this.title = title;
    }

    public String getStpoint() {
        return stpoint;
    }

    public void setStpoint(String stpoint) {
        this.stpoint = stpoint;
    }

    public String getExpr() {
        return expr;
    }

    public void setExpr(String expr) {
        this.expr = expr;
    }

}