package com.webnotes.data.dao.adapters;

import com.webnotes.data.entity.DataEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityPersistenceAdapter<Entity extends DataEntity>  implements DBAdapter<Entity> {

    private static final String CONFIG_NAME = "entity_persistence";

    private static EntityManagerFactory entiryManagerFactory;

    private static void init() {
        if (entiryManagerFactory != null) {
            entiryManagerFactory = Persistence.createEntityManagerFactory(CONFIG_NAME);
        }
    }

    public static void closeDB() {
        entiryManagerFactory.close();
    }

    private EntityManager currentActivity;

    public EntityPersistenceAdapter() {
        init();
    }

    @Override
    public void beginActivity() {
        currentActivity = entiryManagerFactory.createEntityManager();
        currentActivity.getTransaction().begin();
    }

    @Override
    public void commit() {
        currentActivity.getTransaction().commit();
        currentActivity.close();
    }

    @Override
    public void save(Entity object) {
        currentActivity.persist(object);
    }

    @Override
    public void delete(Entity object) {
        currentActivity.remove(object);
    }

    @Override
    public Entity getById(Class entityClass, Long id) {
        return (Entity) currentActivity.find(entityClass, id);
    }

}
