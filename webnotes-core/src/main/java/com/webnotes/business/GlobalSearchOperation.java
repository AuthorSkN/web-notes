package com.webnotes.business;

import com.webnotes.data.entity.Note;

import java.util.Set;

public interface GlobalSearchOperation {

    Set<Note> globalSearch(String searchString);
}
