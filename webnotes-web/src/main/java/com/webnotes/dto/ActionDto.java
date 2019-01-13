package com.webnotes.dto;

public class ActionDto {

    private int key;
    private boolean complete;
    private String text;


    public ActionDto(int key, boolean complete, String text) {
        this.key = key;
        this.complete = complete;
        this.text = text;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
