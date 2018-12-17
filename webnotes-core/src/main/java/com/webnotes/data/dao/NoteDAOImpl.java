package com.webnotes.data.dao;

import com.webnotes.data.dao.adapters.DBAdapter;
import com.webnotes.data.entity.Note;

public final class NoteDAOImpl extends DAO<Note> {

    public NoteDAOImpl(DBAdapter adapter) {
        super(adapter);
    }
}
