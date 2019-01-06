package com.webnotes.data.dao;

import com.webnotes.data.dao.adapters.DBAdapter;
import com.webnotes.data.entity.Group;

public final class GroupDAOImpl extends DAO<Group> {

    public GroupDAOImpl(DBAdapter adapter) {
        super(adapter);
    }
}
