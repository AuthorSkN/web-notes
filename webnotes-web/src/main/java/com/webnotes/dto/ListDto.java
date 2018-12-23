package com.webnotes.dto;

public class ListDto {

    private final NoteHeaderDto[] notes;
    private final GroupDto[] groups;

    public ListDto(NoteHeaderDto[] notes, GroupDto[] groups) {
        this.notes = notes;
        this.groups = groups;
    }
}
