package com.cookandroid.blahblar;

public class LearnInfoVO {
    //id TEXT PRIMARY KEY, name TEXT, passwd TEXT, phone TEXT);
    private String id;
    private int level;
    private String time;
    private int score;
    private int dscore;

    public LearnInfoVO() {
        this.id = id;
        this.level = level;
        this.time = time;
        this.score = score;
        this.dscore=dscore;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int level) {
        this.score = score;
    }

    public int getDscore() {
        return dscore;
    }

    public void setDscore(int dscore) {
        this.dscore = dscore;
    }
}
