package com.webnotes.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.webnotes.data.entity.Note;
import com.webnotes.dto.GroupDto;
import com.webnotes.dto.ListDto;
import com.webnotes.dto.NoteHeaderDto;
import com.webnotes.ejb.GlobalSearchBean;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

@WebServlet(name = "ServletSearch", urlPatterns = {"/search-controller"})
public class GlobalSearchController extends HttpServlet {

    private static final String SEARCH_STRING_PARAMETER = "sstr";
    private static final int NOT_GROUP = -1;
    private static final GroupDto[] EMPTY_GROUPS_DTO = new GroupDto[0];

    @EJB
    private GlobalSearchBean globalSearchBean;


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String responseJSON;
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        String searchString = request.getParameter(SEARCH_STRING_PARAMETER);
        if (searchString.isEmpty()) {
            responseJSON = gson.toJson(new ListDto(new NoteHeaderDto[0], EMPTY_GROUPS_DTO));
        } else {
            Set<Note> selectedNotes = globalSearchBean.globalSerach(searchString.toLowerCase());
            NoteHeaderDto[] noteHeaderDtos = new NoteHeaderDto[selectedNotes.size()];
            int idx = 0;
            for(Note note : selectedNotes) {
                int parentKey = (note.getGroup() == null)? NOT_GROUP : note.getGroup().getId();
                noteHeaderDtos[idx++] = new NoteHeaderDto(note.getId(), note.getName(), parentKey);
            }
            responseJSON = gson.toJson(new ListDto(noteHeaderDtos, EMPTY_GROUPS_DTO));
        }


        PrintWriter out = response.getWriter();
        out.print(responseJSON);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}