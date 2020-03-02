package com.cookandroid.blahblar;

public class RecommVO {
    //id TEXT PRIMARY KEY, name TEXT, passwd TEXT, phone TEXT);
    private String id;
    private String contents;
    private boolean rec;

    public RecommVO() {
        this.id = id;
        this.contents = contents;
        this.rec = rec;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public boolean getRec() {
        return rec;
    }

    public void setRec(boolean rec) {
        this.rec = rec;
    }
}