package com.webnotes.dto;

import java.io.Serializable;

public class NoteHeaderDto implements Serializable {

    private long key;
    private String name;
    private long parentKey;

    public NoteHeaderDto(long key, String name, long parentKey) {
        this.key = key;
        this.name = name;
        this.parentKey = parentKey;
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

    public long getParentKey() {
        return parentKey;
    }

    public void setParentKey(long parentKey) {
        this.parentKey = parentKey;
    }
}
