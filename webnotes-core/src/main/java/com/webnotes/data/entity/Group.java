package com.webnotes.data.entity;

import java.util.Date;

public final class Group {

    private static final int DEFAULT_ID = 0;


    private Integer id = DEFAULT_ID;
    private String name;
    private Date createDate;

    public Group(String name, Date createDate) {
        this.name = name;
        this.createDate = createDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
