package com.webnotes.dto;

import java.io.Serializable;

public class GroupDto implements Serializable {

    private final long key;
    private final String name;
    private final NoteHeaderDto[] notes;


    public GroupDto(long key, String name, NoteHeaderDto[] notes) {
        this.key = key;
        this.name = name;
        this.notes = notes;
    }
}
