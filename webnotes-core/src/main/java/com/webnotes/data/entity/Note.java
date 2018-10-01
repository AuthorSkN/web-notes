package com.webnotes.data.entity;

import java.util.Date;

public class Note {

    private Integer id;
    private String name;
    private String text;
    private Date createDate;

    Note(Integer id, String name, String text, Date createDate) {
        this.id = id;
        this.name = name;
        this.text = text;
        this.createDate = createDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
