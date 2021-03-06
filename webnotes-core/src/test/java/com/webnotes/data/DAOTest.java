package com.webnotes.data;

import com.webnotes.data.entity.*;
import com.webnotes.data.dao.*;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.Date;

public class DAOTest {

    private static final Group GROUP_1 = new Group("Папка 1", new Date());
    private static final Group GROUP_2 = new Group("Папка 2", new Date());

    private static final Note NOTE_1 = new Note("Запись 1", "текст 1", new Date());
    private static final Note NOTE_2 = new Note("Запись 2", "текст 2", new Date());
    private static final Note NOTE_3 = new Note("Запись 3", "текст 3", new Date());

    private static final Action ACTION_1 = new Action("Действие 1", true);
    private static final Action ACTION_2 = new Action("Действие 2", false);
    private static final Action ACTION_3 = new Action("Действие 3", true);


    private void setTestData(DAO<Group> folderDataAccessor, DAO<Note> noteDataAccessor, DAO<Action> actionDataAccessor) {
       /* folderDataAccessor.add(GROUP_1);
        folderDataAccessor.add(GROUP_2);

        NOTE_1.setGroup(GROUP_1);
        NOTE_2.setGroup(GROUP_1);
        NOTE_3.setGroup(GROUP_2);
        noteDataAccessor.add(NOTE_1);
        noteDataAccessor.add(NOTE_2);
        noteDataAccessor.add(NOTE_3);

        ACTION_1.setNote(NOTE_1);
        ACTION_2.setNote(NOTE_1);
        ACTION_3.setNote(NOTE_1);
        actionDataAccessor.add(ACTION_1);
        actionDataAccessor.add(ACTION_2);
        actionDataAccessor.add(ACTION_3);*/
    }

    @Test
    public void addAndDeleteFullStructure() {
        //DAO.init();
/*
        GroupDAOImpl folderDataAccessor = new GroupDAOImpl();
        NoteDAOImpl noteDataAccessor = new NoteDAOImpl();
        ActionDAOImpl actionDataAccessor = new ActionDAOImpl();

        clearTestDataIfExist(folderDataAccessor, noteDataAccessor, actionDataAccessor);
        setTestData(folderDataAccessor, noteDataAccessor, actionDataAccessor);

        System.out.println("all elements are added");

        actionDataAccessor.delete(ACTION_1);
        actionDataAccessor.delete(ACTION_2);
        actionDataAccessor.delete(ACTION_3);
        noteDataAccessor.delete(NOTE_1);
        noteDataAccessor.delete(NOTE_2);
        noteDataAccessor.delete(NOTE_3);
        folderDataAccessor.delete(GROUP_1);
        folderDataAccessor.delete(GROUP_2);

        System.out.println("all elements are removed");

        Assert.assertTrue(folderDataAccessor.getAll().isEmpty());
        Assert.assertTrue(noteDataAccessor.getAll().isEmpty());
        Assert.assertTrue(actionDataAccessor.getAll().isEmpty());

        System.out.println("all tables are empty");

        clearTestDataIfExist(folderDataAccessor, noteDataAccessor, actionDataAccessor);*/
        //DAO.closeDB();
    }

    @Test
    public void cascadeDeleteNoteByFolder() {
        //DAO.init();

        /*GroupDAOImpl folderDataAccessor = new GroupDAOImpl();
        NoteDAOImpl noteDataAccessor = new NoteDAOImpl();
        ActionDAOImpl actionDataAccessor = new ActionDAOImpl();

        clearTestDataIfExist(folderDataAccessor, noteDataAccessor, actionDataAccessor);
        setTestData(folderDataAccessor, noteDataAccessor, actionDataAccessor);

        folderDataAccessor.delete(GROUP_1);

        Assert.assertFalse(folderDataAccessor.getAll().contains(GROUP_1));
        Assert.assertFalse(noteDataAccessor.getAll().contains(NOTE_1));
        Assert.assertFalse(noteDataAccessor.getAll().contains(NOTE_2));
        Assert.assertTrue(actionDataAccessor.getAll().isEmpty());

        folderDataAccessor.delete(GROUP_2);

        clearTestDataIfExist(folderDataAccessor, noteDataAccessor, actionDataAccessor);*/
        //DAO.closeDB();

    }

    @Test
    public void entityManagerTest() {
        EntityManager em = Persistence.createEntityManagerFactory("test-unit").createEntityManager();
        em.getTransaction().begin();
        Group group = (Group) em.find(Group.class, 48);
        em.getTransaction().commit();
        em.close();
    }

    private void clearTestDataIfExist(DAO<Group> folderDataAccessor, DAO<Note> noteDataAccessor, DAO<Action> actionDataAccessor) {
        actionDataAccessor.deleteAll(actionDataAccessor.getAll());
        noteDataAccessor.deleteAll(noteDataAccessor.getAll());
        folderDataAccessor.deleteAll(folderDataAccessor.getAll());
    }



}
