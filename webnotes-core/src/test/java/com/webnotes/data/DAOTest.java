package com.webnotes.data;

import com.webnotes.data.entity.*;
import com.webnotes.data.dao.*;
import org.junit.Test;

import java.util.Date;

public class DAOTest {

    private static final Folder FOLDER_1 = new Folder("Папка 1", new Date());
    private static final Folder FOLDER_2 = new Folder("Папка 2", new Date());

    private static final Note NOTE_1 = new Note("Запись 1", "текст 1", new Date());
    private static final Note NOTE_2 = new Note("Запись 2", "текст 2", new Date());
    private static final Note NOTE_3 = new Note("Запись 3", "текст 3", new Date());

    private static final Action ACTION_1 = new Action("Действие 1", true);
    private static final Action ACTION_2 = new Action("Действие 2", false);
    private static final Action ACTION_3 = new Action("Действие 3", true);


    @Test
    public void addAndDeleteFullStructure()
    {
        DAO.init();

        FolderDAOImpl folderDataAccessor = new FolderDAOImpl();
        NoteDAOImpl noteDataAccessor = new NoteDAOImpl();
        ActionDAOImpl actionDataAccessor = new ActionDAOImpl();

        folderDataAccessor.add(FOLDER_1);
        folderDataAccessor.add(FOLDER_2);

        NOTE_1.setFolder(FOLDER_1);
        NOTE_2.setFolder(FOLDER_1);
        NOTE_3.setFolder(FOLDER_2);
        noteDataAccessor.add(NOTE_1);
        noteDataAccessor.add(NOTE_2);
        noteDataAccessor.add(NOTE_3);

        ACTION_1.setNote(NOTE_1);
        ACTION_2.setNote(NOTE_1);
        ACTION_3.setNote(NOTE_1);
        actionDataAccessor.add(ACTION_1);
        actionDataAccessor.add(ACTION_2);
        actionDataAccessor.add(ACTION_3);

        System.out.println("all elements are added");

        actionDataAccessor.delete(ACTION_1);
        actionDataAccessor.delete(ACTION_2);
        actionDataAccessor.delete(ACTION_3);
        noteDataAccessor.delete(NOTE_1);
        noteDataAccessor.delete(NOTE_2);
        noteDataAccessor.delete(NOTE_3);
        folderDataAccessor.delete(FOLDER_1);
        folderDataAccessor.delete(FOLDER_2);

        DAO.closeDB();
    }

    @Test
    public void cascadeDeleteForNoteFromFolder() {

    }

}
