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

@WebServlet(name = "ServletNote", urlPatterns = {"/note-controller"})
public class NoteController extends HttpServlet {

    private static final int OPERATION_LOAD = 0;

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

    private String loadOperation() {
        DAO<Note> noteDataAccessor = dataFactory.createNoteDAO();
        DAO<Folder> groupsDataAccessor = dataFactory.createFolderDAO();

        List<Note> notesData = noteDataAccessor.getAll();
        List<Folder> groupsData = groupsDataAccessor.getAll();

        List<NoteHeaderDto> noteHeaderDtoList = new ArrayList<>();
        List<GroupDto> groupDtoList = new ArrayList<>();

        for(Note note: notesData) {
            Folder group = note.getGroup();
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
}
