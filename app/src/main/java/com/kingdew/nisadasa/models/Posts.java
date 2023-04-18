package com.kingdew.nisadasa.models;

public class Posts {

    String name,date,desc,hearts;

    public Posts() {
    }

    public Posts(String name, String date, String desc, String hearts) {
        this.name = name;
        this.date = date;
        this.desc = desc;
        this.hearts = hearts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getHearts() {
        return hearts;
    }

    public void setHearts(String hearts) {
        this.hearts = hearts;
    }
}
