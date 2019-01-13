package com.webnotes.dto;

public class NoteDto {

    private int key;
    private int parentKey;
    private String name;
    private String text;
    private ActionDto[] actions;

    public NoteDto(int key, int parentKey, String name, String text, ActionDto[] actions) {
        this.key = key;
        this.parentKey = parentKey;
        this.name = name;
        this.text = text;
        this.actions = actions;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getParentKey() {
        return parentKey;
    }

    public void setParentKey(int parentKey) {
        this.parentKey = parentKey;
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

    public ActionDto[] getActions() {
        return actions;
    }

    public void setActions(ActionDto[] actions) {
        this.actions = actions;
    }
}
