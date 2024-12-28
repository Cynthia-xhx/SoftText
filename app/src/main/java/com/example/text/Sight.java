package com.example.text;

public class Sight {
    private String name;
    private String description;
    private int picid;

    public Sight() {
    }

    public Sight(String name, String description, int picid) {
        this.name = name;
        this.description = description;
        this.picid = picid;
    }

    public Sight(String s, String s1) {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPicid() {
        return picid;
    }

    public void setPicid(int picid) {
        this.picid = picid;
    }

    @Override
    public String toString() {
        return String.format("%d %s %s",picid,name,description);
    }
}
