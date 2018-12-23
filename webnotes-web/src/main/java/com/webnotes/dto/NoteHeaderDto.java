package com.webnotes.dto;

import java.io.Serializable;

public class NoteHeaderDto implements Serializable {

    private final long key;
    private final String name;

    public NoteHeaderDto(long key, String name) {
        this.key = key;
        this.name = name;
    }
}
