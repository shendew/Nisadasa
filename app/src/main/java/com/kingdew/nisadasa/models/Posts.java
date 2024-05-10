package com.kingdew.nisadasa.models;

public class Posts {

    String key,name,date,desc,hearts,image,page;

    public Posts() {
    }

    public Posts(String key, String name, String date, String desc, String hearts, String image, String page) {
        this.key = key;
        this.name = name;
        this.date = date;
        this.desc = desc;
        this.hearts = hearts;
        this.image = image;
        this.page = page;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
}
