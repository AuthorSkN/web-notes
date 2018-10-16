package com.webnotes.data.dao;

import com.webnotes.data.entity.DataEntity;
import com.webnotes.exceptions.WebNotesException;
import com.webnotes.exceptions.WebNotesExceptionFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.lang.reflect.ParameterizedType;

import java.util.List;

public abstract class DAO<E extends DataEntity> {

    private static final String SHUTDOWN_QUERY = "SHUTDOWN";

    private final Class entityClass;

    protected static SessionFactory sessionFactory;


    public static void init() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
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

    protected DAO(){
        this.entityClass = getEntityClass();
    }


    protected Session beginActivity() throws WebNotesException {
        Session session;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
        } catch (HibernateException exc) {
            exc.printStackTrace();
            throw WebNotesExceptionFactory.createConnectivityException();
        }
        return session;
    }


    protected void commit(Session activity) throws WebNotesException {
        try {
            activity.getTransaction().commit();
            activity.close();
        } catch (HibernateException exc) {
            exc.printStackTrace();
            throw WebNotesExceptionFactory.createConnectivityException();
        }
    }


    public void add(E item) throws WebNotesException {
        Session activity = beginActivity();
        activity.save(item);
        commit(activity);
    }

    public void delete(E item) throws WebNotesException {
        Session activity = beginActivity();
        activity.delete(item);
        commit(activity);
    }

    public E getById(Long id) throws WebNotesException{
        Session activity = beginActivity();
        E entity = (E) activity.load(getEntityClass(), id);
        commit(activity);
        return entity;
    }

    public List<E> getAll() throws WebNotesException{
        Session activity = beginActivity();
        String getAllQuery = "From " +  this.entityClass.getSimpleName();
        List<E> entityList = activity.createQuery(getAllQuery).list();
        commit(activity);
        return entityList;
    }

    public void deleteAll(List<E> entries) throws WebNotesException {
        Session activity = beginActivity();
        entries.forEach(activity::delete);
        commit(activity);
    }

    private Class getEntityClass() {
        return (Class) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

}
