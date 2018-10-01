package com.webnotes.data.entity;

public class Action {

    private Integer id;
    private String text;
    private Boolean passed;

    Action(Integer id, String text, Boolean passed) {
        this.id = id;
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
