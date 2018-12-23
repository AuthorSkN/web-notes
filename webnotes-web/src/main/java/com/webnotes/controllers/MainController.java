package com.webnotes.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.webnotes.data.dao.DAO;
import com.webnotes.data.dao.DAOFactory;
import com.webnotes.data.dao.NoteDAOImpl;
import com.webnotes.data.entity.Folder;
import com.webnotes.data.entity.Note;
import com.webnotes.dto.GroupDto;
import com.webnotes.dto.NoteHeaderDto;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

@WebServlet(name="ServletM", urlPatterns = {"/hello"})
public class MainController extends HttpServlet
{
    private DAOFactory dataFactory = new DAOFactory(DAOFactory.ENTITY_PERSISTENCE_ADAPTER);

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DAO<Note> noteDataAccessor = dataFactory.createNoteDAO();
        DAO<Folder> folderDataAccessor = dataFactory.createFolderDAO();

        List<Note> notes = noteDataAccessor.getAll();

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        String json = gson.toJson(new GroupDto(1, "super_name", new NoteHeaderDto[]{new NoteHeaderDto(1, "note1")}));
        PrintWriter out = response.getWriter();
        out.print(json);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        doGet(request, response);
    }
}
