package com.webnotes.data.dao;

import com.webnotes.data.dao.adapters.DBAdapter;
import com.webnotes.data.entity.Log;

public class LogDAOImpl extends DAO<Log> {

    public LogDAOImpl(DBAdapter adapter) {
        super(adapter);
    }

}
