package com.webnotes.business;

import com.webnotes.data.dao.DAO;
import com.webnotes.data.dao.DAOFactory;
import com.webnotes.data.entity.Action;
import com.webnotes.data.entity.Note;

import javax.ejb.Local;
import javax.ejb.Stateless;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GlobalSearchBean {

    private DAOFactory dataFactory = new DAOFactory(DAOFactory.HIBERNATE_ADAPTER);

    public GlobalSearchBean() {
    }

    public Set<Note> globalSerach(String searchString) {
        DAO<Note> noteDataAccessor = dataFactory.createNoteDAO();

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
