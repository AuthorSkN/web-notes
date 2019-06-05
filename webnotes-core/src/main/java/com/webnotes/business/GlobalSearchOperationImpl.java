package com.webnotes.business;

import com.webnotes.data.dao.DAO;
import com.webnotes.data.dao.DAOFactory;
import com.webnotes.data.dao.NoteDAOImpl;
import com.webnotes.data.entity.Action;
import com.webnotes.data.entity.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class GlobalSearchOperationImpl {

    @Autowired
    private NoteDAOImpl noteDataAccessor;

    public GlobalSearchOperationImpl() {
    }

    public Set<Note> globalSearch(String searchString) {
        List<Note> allNotes = noteDataAccessor.getAll();
        Set<Note> selectedNotes = new HashSet<>();

        for(Note note: allNotes) {
            if ((note.getName().contains(searchString))||
                    (note.getText().contains(searchString))||
                    actionsContain(note, searchString))
            {
                selectedNotes.add(note);
            }
        }
        return selectedNotes;
    }

    private boolean actionsContain(Note note, String searchString) {
        for(Action action: note.getActions()) {
            if (action.getText().contains(searchString))
                return true;
        }
        return false;
    }

}
