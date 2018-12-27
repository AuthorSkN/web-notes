package com.webnotes.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.webnotes.data.dao.DAO;
import com.webnotes.data.dao.DAOFactory;
import com.webnotes.data.entity.Folder;
import com.webnotes.data.entity.Note;
import com.webnotes.dto.GroupDto;
import com.webnotes.dto.ListDto;
import com.webnotes.dto.NoteHeaderDto;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@WebServlet(name="ServletM", urlPatterns = {"/list-controller"})
public class MainController extends HttpServlet
{
    private static final int OPERATION_LOAD = 0;
    private static final int OPERATION_ADD_NOTE = 1;
    private static final int OPERATION_ADD_GROUP = 2;
    private static final int OPERATION_REMOVE_NOTE = 3;
    private static final int OPERATION_REMOVE_GROUP = 4;

    private static final String EMPTY_TEXT = "";
    private static final long NOT_GROUP = -1;


    private DAOFactory dataFactory = new DAOFactory(DAOFactory.ENTITY_PERSISTENCE_ADAPTER);

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int operation = Integer.parseInt(request.getParameter("oper"));
        String responseJSON = "";

        switch (operation) {
            case OPERATION_LOAD: {
                responseJSON = loadOperation();
            } break;
            case OPERATION_ADD_NOTE: {
                String name = request.getParameter("name");
                String groupKey = request.getParameter("group");
                responseJSON = addNoteOperation(name, groupKey);
            }break;
            case OPERATION_ADD_GROUP: {
                String name = request.getParameter("name");
                responseJSON = addGroupOperation(name);
            }break;
            case OPERATION_REMOVE_NOTE: {
                int noteKey = Integer.parseInt(request.getParameter("key"));
                responseJSON = removeNoteOperation(noteKey);
            }break;
            case OPERATION_REMOVE_GROUP: {
                int groupKey = Integer.parseInt(request.getParameter("key"));
                responseJSON = removeGroupOperation(groupKey);
            }break;
        }

        PrintWriter out = response.getWriter();
        out.print(responseJSON);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        doGet(request, response);
    }

    private String loadOperation() {
        DAO<Note> noteDataAccessor = dataFactory.createNoteDAO();
        DAO<Folder> groupsDataAccessor = dataFactory.createFolderDAO();

        List<Note> notesData = noteDataAccessor.getAll();
        List<Folder> groupsData = groupsDataAccessor.getAll();

        List<NoteHeaderDto> noteHeaderDtoList = new ArrayList<>();
        List<GroupDto> groupDtoList = new ArrayList<>();

        for(Note note: notesData) {
            Folder group = note.getFolder();
            if (group == null) {
                noteHeaderDtoList.add(new NoteHeaderDto(note.getId(), note.getName(), NOT_GROUP));
            }
        }

        for(Folder group: groupsData) {
            Set<Note> notesOfGroup = group.getNotes();
            NoteHeaderDto[] noteHeaderDtosForGroup = new NoteHeaderDto[notesOfGroup.size()];
            int idx = 0;
            for(Note noteOfGroup : notesOfGroup) {
                noteHeaderDtosForGroup[idx++] = new NoteHeaderDto(noteOfGroup.getId(), noteOfGroup.getName(), group.getId());
            }
            groupDtoList.add(new GroupDto(group.getId(), group.getName(), noteHeaderDtosForGroup));
        }

        NoteHeaderDto[] noteHeaderDtos = noteHeaderDtoList.toArray(new NoteHeaderDto[noteHeaderDtoList.size()]);
        GroupDto[] groupDtos = groupDtoList.toArray(new GroupDto[groupDtoList.size()]);

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        return gson.toJson(new ListDto( noteHeaderDtos, groupDtos));
    }

    private String addNoteOperation(String name, String groupKeyString) {
        DAO<Note> noteDataAccessor = dataFactory.createNoteDAO();

        Note note = new Note(name, EMPTY_TEXT, new Date());
        if (!groupKeyString.isEmpty()) {
            int groupKey = Integer.parseInt(groupKeyString);
            DAO<Folder> folderDataAccessor = dataFactory.createFolderDAO();
            Folder parentGroup = folderDataAccessor.getById(groupKey);
            note.setFolder(parentGroup);
        }
        noteDataAccessor.add(note);

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        return gson.toJson(new NoteHeaderDto(note.getId(),
                note.getName(),
                (note.getFolder() == null)? NOT_GROUP : note.getFolder().getId()));
    }

    private String addGroupOperation(String name) {
        DAO<Folder> folderDataAccessor = dataFactory.createFolderDAO();

        Folder group = new Folder(name, new Date());
        folderDataAccessor.add(group);

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        return gson.toJson(new GroupDto(group.getId(), group.getName(), new NoteHeaderDto[]{}));
    }

    private String removeNoteOperation(int noteKey) {
        DAO<Note> noteDataAccessor = dataFactory.createNoteDAO();

        Note note = noteDataAccessor.getById(noteKey);
        noteDataAccessor.delete(note);

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        return gson.toJson(noteKey);
    }

    private String removeGroupOperation(int groupKey) {
        DAO<Folder> folderDataAccessor = dataFactory.createFolderDAO();

        Folder group = folderDataAccessor.getById(groupKey);
        folderDataAccessor.delete(group);

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        return gson.toJson(groupKey);
    }
}
