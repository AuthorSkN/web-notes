package com.webnotes.data.entity;

public final class Action {

    private static final int DEFAULT_ID = 0;


    private Integer id = DEFAULT_ID;
    private String text;
    private Boolean passed;

    public Action(String text, Boolean passed) {
        this.text = text;
        this.passed = passed;
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
