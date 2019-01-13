package com.webnotes.controllers;

import com.webnotes.data.dao.DAO;
import com.webnotes.data.dao.DAOFactory;
import com.webnotes.data.entity.Action;
import com.webnotes.data.entity.Note;
import com.webnotes.dto.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@RestController
public class NoteController {

    private static final int OPERATION_LOAD = 0;
    private static final int OPERATION_CHECK_ACTION = 1;
    private static final int OPERATION_CHANGE_NOTE = 2;

    private static final int NOT_GROUP = -1;


    private DAOFactory dataFactory = new DAOFactory(DAOFactory.HIBERNATE_ADAPTER);


    @RequestMapping(value = "/loadNote", headers = "Accept=application/json", method = RequestMethod.GET)
    @ResponseBody
    public NoteDto loadNoteOperation(@RequestParam(value = "key") int noteKey) {
        DAO<Note> noteDataAccessor = dataFactory.createNoteDAO();

        Note note = noteDataAccessor.getById(noteKey);

        Set<Action> noteActions = note.getActions();
        ActionDto[] actionDtos = new ActionDto[noteActions.size()];

        int idx = 0;
        for(Action action: noteActions) {
            actionDtos[idx++] = new ActionDto(action.getId(), action.getPassed(), action.getText());
        }

        int parentKey = (note.getGroup() == null)? NOT_GROUP : note.getGroup().getId();

        return new NoteDto(note.getId(), parentKey, note.getName(), note.getText(), actionDtos);
    }

    @RequestMapping(value = "/checkAction", headers = "Accept=application/json", method = RequestMethod.GET)
    @ResponseBody
    public Boolean checkActionOperation(@RequestParam(value = "action") int actionKey,
                                        @RequestParam(value = "complete") boolean complete) {
        DAO<Action> actionDataAccessor = dataFactory.createActionDAO();

        Action action = actionDataAccessor.getById(actionKey);
        action.setPassed(complete);
        actionDataAccessor.update(action);

        return complete;
    }

    @RequestMapping(value = "/changeNote", headers = "Accept=application/json", method = RequestMethod.GET)
    @ResponseBody
    public NoteDto changeNoteOperation(@RequestParam(value = "key") int noteKey,
                                       @RequestParam(value = "name") String name,
                                       @RequestParam(value = "text") String text,
                                       @RequestParam(value = "actions") String actionTextStr) {
        DAO<Note> noteDataAccessor = dataFactory.createNoteDAO();

        String[] actionTexts = actionTextStr.split(";");
        Note note = noteDataAccessor.getById(noteKey);
        if (actionTexts.length != 0) {
            DAO<Action> actionDataAccessor = dataFactory.createActionDAO();

            for (Action action: note.getActions()) {
                actionDataAccessor.delete(action);
            }
            note.getActions().clear();

            for(String newActionText: actionTexts) {
                Action newAction = new Action(newActionText, false);
                newAction.setNote(note);
                note.getActions().add(newAction);
                actionDataAccessor.add(newAction);
            }
        }

        note.setName(name);
        note.setText(text);
        Note newNote = noteDataAccessor.update(note);

        ActionDto[] actionDtos = new ActionDto[actionTexts.length];
        int idx = 0;
        for (Action newAction: newNote.getActions()) {
            actionDtos[idx++] = new ActionDto(newAction.getId(), false, newAction.getText());
        }

        int parentKey = (newNote.getGroup() == null)? NOT_GROUP : newNote.getGroup().getId();

        return new NoteDto(newNote.getId(), parentKey, newNote.getName(), newNote.getText(), actionDtos);
    }
}
