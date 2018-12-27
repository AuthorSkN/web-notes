package com.webnotes.data.dao.adapters;

import com.webnotes.data.entity.DataEntity;

import java.util.List;

public interface DBAdapter<Entity extends DataEntity> {


    void beginActivity();

    void commit();

    void save(Entity object);

    void delete(Entity object);

    Entity getById(Class entityClass, Integer id);

    List<Entity> executeQuery(String query);

    List<Entity> executeSelectQuery(String whereQuery, Class entityClass, String alias);

}
