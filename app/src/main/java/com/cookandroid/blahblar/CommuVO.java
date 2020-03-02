package com.cookandroid.blahblar;

public class CommuVO {
    //id TEXT PRIMARY KEY, name TEXT, passwd TEXT, phone TEXT);
    //private String no;
    private String id;
    private String time;
    private String board;
    private String contents;

    public CommuVO() {
        //this.no=no;
        this.id = id;
        this.time = time;
        this.board=board;
        this.contents=contents;
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

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

}
