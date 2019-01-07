package com.webnotes.data.dao;

import com.webnotes.data.dao.adapters.DBAdapter;
import com.webnotes.data.entity.DataEntity;
import com.webnotes.exceptions.WebNotesException;
import org.hibernate.Session;

import java.lang.reflect.ParameterizedType;

import java.util.List;

public abstract class DAO<Entity extends DataEntity>{

    private final Class entityClass;
    protected final DBAdapter<Entity> dbAdapter;

    protected DAO(DBAdapter adapter){
        this.entityClass = getEntityClass();
        this.dbAdapter = adapter;
    }


    public void add(Entity item) throws WebNotesException {
        dbAdapter.beginActivity();
        dbAdapter.save(item);
        dbAdapter.commit();
    }

    public void delete(Entity item) throws WebNotesException {
        dbAdapter.beginActivity();
        dbAdapter.delete(item);
        dbAdapter.commit();
    }

    public Entity getById(int id) throws WebNotesException{
        dbAdapter.beginActivity();
        Entity object = dbAdapter.getById(getEntityClass(), id);
        dbAdapter.commit();
        return object;
    }

    public List<Entity> getAll() throws WebNotesException{
        dbAdapter.beginActivity();
        List<Entity> entityList = dbAdapter.executeSelectQuery("", getEntityClass(), "a");
        dbAdapter.commit();
        return entityList;
    }

    public void deleteAll(List<Entity> entries) throws WebNotesException {
        dbAdapter.beginActivity();
        entries.forEach(dbAdapter::delete);
        dbAdapter.commit();
    }

    public Entity update(Entity item) throws WebNotesException {
        Entity updateItem;
        dbAdapter.beginActivity();
        updateItem = dbAdapter.update(item);
        dbAdapter.commit();
        return updateItem;
    }

    protected Class getEntityClass() {
        return (Class) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }


}
