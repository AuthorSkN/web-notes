package com.webnotes.data.dao;

import com.webnotes.data.dao.adapters.DBAdapter;
import com.webnotes.data.entity.Action;

public final class ActionDAOImpl extends DAO<Action> {

    public ActionDAOImpl(DBAdapter adapter) {
        super(adapter);
    }
}
