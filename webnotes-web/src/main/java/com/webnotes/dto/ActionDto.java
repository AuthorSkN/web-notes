package com.webnotes.dto;

public class ActionDto {

    private final int key;
    private final boolean complete;
    private final String text;


    public ActionDto(int key, boolean complete, String text) {
        this.key = key;
        this.complete = complete;
        this.text = text;
    }
}
