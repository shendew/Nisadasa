package com.kingdew.nisadasa.models;

public class Category {

    int cateID;
    String cateTitle;

    public Category() {
    }

    public Category(int cateID, String cateTitle) {
        this.cateID = cateID;
        this.cateTitle = cateTitle;
    }

    public int getCateID() {
        return cateID;
    }

    public void setCateID(int cateID) {
        this.cateID = cateID;
    }

    public String getCateTitle() {
        return cateTitle;
    }

    public void setCateTitle(String cateTitle) {
        this.cateTitle = cateTitle;
    }
}
