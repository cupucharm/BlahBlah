package com.cookandroid.blahblar;

class VideoListData {

    private int img;
    private String text;

    public VideoListData(int img, String text){
        this.img = img;
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public int getImg() {
        return this.img;
    }
}


