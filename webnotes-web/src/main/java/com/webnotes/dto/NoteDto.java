package com.webnotes.dto;

public class NoteDto {

    private final int key;
    private final int parentKey;
    private final String name;
    private final String text;
    private final ActionDto[] actions;

    public NoteDto(int key, int parentKey, String name, String text, ActionDto[] actions) {
        this.key = key;
        this.parentKey = parentKey;
        this.name = name;
        this.text = text;
        this.actions = actions;
    }
}
