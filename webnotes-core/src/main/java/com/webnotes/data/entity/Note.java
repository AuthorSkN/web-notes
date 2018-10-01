package com.webnotes.data.entity;

import java.util.Date;

public final class Note {

    private static final int DEFAULT_ID = 0;

    private Integer id = DEFAULT_ID;
    private String name;
    private String text;
    private Date createDate;

    public Note(String name, String text, Date createDate) {
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
