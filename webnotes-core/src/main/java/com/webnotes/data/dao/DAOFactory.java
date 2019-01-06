package com.webnotes.data.dao;

import com.webnotes.data.dao.adapters.EntityPersistenceAdapter;
import com.webnotes.data.dao.adapters.HibernateAdapter;

public class DAOFactory {

    public static final boolean HIBERNATE_ADAPTER = true;
    public static final boolean ENTITY_PERSISTENCE_ADAPTER = false;

    private final boolean config;

    public DAOFactory(boolean config) {
        this.config = config;
    }

    public GroupDAOImpl createGroupDAO() {
        if (config) {
            return new GroupDAOImpl(new HibernateAdapter());
        } else {
            return new GroupDAOImpl(new EntityPersistenceAdapter());
        }
    }

    public NoteDAOImpl createNoteDAO() {
        if (config) {
            return new NoteDAOImpl(new HibernateAdapter());
        } else {
            return new NoteDAOImpl(new EntityPersistenceAdapter());
        }
    }

    public ActionDAOImpl createActionDAO() {
        if (config) {
            return new ActionDAOImpl(new HibernateAdapter());
        } else {
            return new ActionDAOImpl(new EntityPersistenceAdapter());
        }
    }

    public void closeDB() {
        if (config) {
            HibernateAdapter.closeDB();
        } else {
            EntityPersistenceAdapter.closeDB();
        }
    }

}
