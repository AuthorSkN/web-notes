package com.webnotes.data.entity;

public final class Action implements DataEntity{

    private static final int DEFAULT_ID = 0;


    private Integer id = DEFAULT_ID;
    private String text;
    private Boolean passed;

    public Action(String text, Boolean passed) {
        this.text = text;
        this.passed = passed;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getPassed() {
        return passed;
    }

    public void setPassed(Boolean passed) {
        this.passed = passed;
    }
}
