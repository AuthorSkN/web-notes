package com.webnotes.data.dao.adapters;

import com.webnotes.data.entity.DataEntity;

public interface DBAdapter<Entity extends DataEntity> {


    void beginActivity();

    void commit();

    void save(Entity object);

    void delete(Entity object);

    Entity getById(Class entityClass, Long id);


}
