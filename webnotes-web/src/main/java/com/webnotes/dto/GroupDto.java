package com.webnotes.dto;

import java.io.Serializable;

public class GroupDto implements Serializable {

    private long key;
    private String name;
    private NoteHeaderDto[] notes;


    public GroupDto(long key, String name, NoteHeaderDto[] notes) {
        this.key = key;
        this.name = name;
        this.notes = notes;
    }

    public long getKey() {
        return key;
    }

    public void setKey(long key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public NoteHeaderDto[] getNotes() {
        return notes;
    }

    public void setNotes(NoteHeaderDto[] notes) {
        this.notes = notes;
    }
}
