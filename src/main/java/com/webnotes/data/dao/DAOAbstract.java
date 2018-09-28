package com.webnotes.data.dao;

import com.webnotes.exceptions.WebNotesExceptionFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public abstract class DAOAbstract {

    private static final String SHUTDOWN_QUERY = "SHUTDOWN";


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

}
