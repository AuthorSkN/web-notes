package com.webnotes.data.dao.adapters;


import com.webnotes.data.entity.DataEntity;
import com.webnotes.exceptions.WebNotesExceptionFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class HibernateAdapter<Entity extends DataEntity> implements DBAdapter<Entity>{

    private static final String SHUTDOWN_QUERY = "SHUTDOWN";

    private static SessionFactory sessionFactory;
    private Session currentSession;

    private static void init() {
        if (sessionFactory == null) {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }
    }

    public static void closeDB() {
        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.createSQLQuery(SHUTDOWN_QUERY);
            session.getTransaction().commit();
            session.close();
            sessionFactory.close();
        } catch (HibernateException exc) {
            exc.printStackTrace();
            throw WebNotesExceptionFactory.createConnectivityException();
        }
    }

    public HibernateAdapter() {
        init();
    }

    @Override
    public void beginActivity() {
        try {
            currentSession = sessionFactory.openSession();
            currentSession.beginTransaction();
        } catch (HibernateException exc) {
            exc.printStackTrace();
            throw WebNotesExceptionFactory.createConnectivityException();
        }
    }

    @Override
    public void commit() {
        try {
            currentSession.getTransaction().commit();
            currentSession.close();
        } catch (HibernateException exc) {
            exc.printStackTrace();
            throw WebNotesExceptionFactory.createConnectivityException();
        }
    }

    @Override
    public Entity getById(Class entityClass, Long id) {
        return (Entity) currentSession.load(entityClass, id);
    }

    @Override
    public List<Entity> executeQuery(String query) {
        return null;
    }

    @Override
    public List<Entity> executeSelectQuery(String whereQuery, Class entityClass, String alias) {
        return null;
    }

    @Override
    public void save(Entity object) {
        currentSession.save(object);
    }

    @Override
    public void delete(Entity object) {
        currentSession.delete(object);
    }


}
