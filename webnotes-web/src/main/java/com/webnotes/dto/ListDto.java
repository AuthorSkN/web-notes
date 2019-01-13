package com.webnotes.dto;

public class ListDto {

    private NoteHeaderDto[] notes;
    private GroupDto[] groups;

    public ListDto(NoteHeaderDto[] notes, GroupDto[] groups) {
        this.notes = notes;
        this.groups = groups;
    }

    public NoteHeaderDto[] getNotes() {
        return notes;
    }

    public void setNotes(NoteHeaderDto[] notes) {
        this.notes = notes;
    }

    public GroupDto[] getGroups() {
        return groups;
    }

    public void setGroups(GroupDto[] groups) {
        this.groups = groups;
    }
}
