package com.webnotes.data.dao.adapters;

import com.webnotes.data.entity.DataEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;

public class EntityPersistenceAdapter<Entity extends DataEntity>  implements DBAdapter<Entity> {

    private static final String CONFIG_NAME = "entity_persistence";

    private static EntityManagerFactory entiryManagerFactory;

    private static void init() {
        if (entiryManagerFactory == null) {
            entiryManagerFactory = Persistence.createEntityManagerFactory(CONFIG_NAME);
        }
    }

    public static void closeDB() {
        entiryManagerFactory.close();
    }

    private EntityManager currenteEntityManager;

    public EntityPersistenceAdapter() {
        init();
    }

    @Override
    public void beginActivity() {
        currenteEntityManager = entiryManagerFactory.createEntityManager();
        currenteEntityManager.getTransaction().begin();
    }

    @Override
    public void commit() {
        currenteEntityManager.getTransaction().commit();
        currenteEntityManager.close();
    }

    @Override
    public void save(Entity object) {
        currenteEntityManager.persist(object);
    }

    @Override
    public void delete(Entity object) {
        Entity obj = currenteEntityManager.merge(object);
        currenteEntityManager.remove(obj);
    }

    @Override
    public Entity getById(Class entityClass, Integer id) {
        //return (Entity) currenteEntityManager.find(entityClass, id);
        Query query = currenteEntityManager.createQuery("SELECT a FROM "+entityClass.getSimpleName() + "AS a where a.id:k");
        query.setParameter("k", id);
        return (Entity)query.getResultList().get(0);
    }

    @Override
    public List<Entity> executeQuery(String query) {
        return (List<Entity>)currenteEntityManager.createQuery(query).getResultList();
    }

    @Override
    public List<Entity> executeSelectQuery(String whereQuery, Class entityClass, String alias) {
        String query = "SELECT "+alias+" FROM "+entityClass.getSimpleName()+" AS "+alias;
        return (List<Entity>)currenteEntityManager.createQuery(query, entityClass).getResultList();
    }


}
