package com.webnotes.dto;

import java.io.Serializable;

public class NoteHeaderDto implements Serializable {

    private final long key;
    private final String name;
    private final long parentKey;

    public NoteHeaderDto(long key, String name, long parentKey) {
        this.key = key;
        this.name = name;
        this.parentKey = parentKey;
    }
}
