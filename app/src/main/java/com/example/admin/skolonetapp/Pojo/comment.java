package com.example.admin.skolonetapp.Pojo;

/**
 * Created by Admin on 10-02-2018.
 */

public class comment {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    int id;
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    String remark;

    public String getText() {
        return description;
    }

    public void setText(String text) {
        this.description = text;
    }

    String description;

}
