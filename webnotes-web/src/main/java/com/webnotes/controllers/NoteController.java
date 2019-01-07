package com.webnotes.controllers;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.webnotes.data.dao.DAO;
import com.webnotes.data.dao.DAOFactory;
import com.webnotes.data.entity.Action;
import com.webnotes.data.entity.Note;
import com.webnotes.dto.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@WebServlet(name = "ServletNote", urlPatterns = {"/note-controller"})
public class NoteController extends HttpServlet {

    private static final int OPERATION_LOAD = 0;
    private static final int OPERATION_CHECK_ACTION = 1;
    private static final int OPERATION_CHANGE_NOTE = 2;

    private static final int NOT_GROUP = -1;


    private DAOFactory dataFactory = new DAOFactory(DAOFactory.HIBERNATE_ADAPTER);

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int operation = Integer.parseInt(request.getParameter("oper"));
        int noteKey = Integer.parseInt(request.getParameter("key"));
        String responseJSON = "";

        switch (operation) {
            case OPERATION_LOAD: {
                responseJSON = loadOperation(noteKey);
            }
            break;
            case OPERATION_CHECK_ACTION: {
                int actionKey = Integer.parseInt(request.getParameter("action"));
                boolean complete = Boolean.parseBoolean(request.getParameter("complete"));
                responseJSON = checkActionOperation(actionKey, complete);
            }
            break;
            case OPERATION_CHANGE_NOTE: {
                String name = request.getParameter("name");
                String text = request.getParameter("text");
                String actions = request.getParameter("actions");
                String[] actionArray = actions.split(";");
                responseJSON = changeNoteOperation(noteKey, name, text, actionArray);
            }
            break;
        }

        PrintWriter out = response.getWriter();
        out.print(responseJSON);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private String loadOperation(int noteKey) {
        DAO<Note> noteDataAccessor = dataFactory.createNoteDAO();

        Note note = noteDataAccessor.getById(noteKey);

        Set<Action> noteActions = note.getActions();
        ActionDto[] actionDtos = new ActionDto[noteActions.size()];

        int idx = 0;
        for(Action action: noteActions) {
            actionDtos[idx++] = new ActionDto(action.getId(), action.getPassed(), action.getText());
        }

        int parentKey = (note.getGroup() == null)? NOT_GROUP : note.getGroup().getId();

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        return gson.toJson(new NoteDto(note.getId(), parentKey, note.getName(), note.getText(), actionDtos));
    }

    private String checkActionOperation(int actionKey, boolean complete) {
        DAO<Action> actionDataAccessor = dataFactory.createActionDAO();

        Action action = actionDataAccessor.getById(actionKey);
        action.setPassed(complete);
        actionDataAccessor.update(action);

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        return gson.toJson(complete);
    }

    private String changeNoteOperation(int noteKey, String name, String text, String[] actionTexts) {
        DAO<Note> noteDataAccessor = dataFactory.createNoteDAO();

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

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        return gson.toJson(new NoteDto(newNote.getId(), parentKey, newNote.getName(), newNote.getText(), actionDtos));
    }
}
