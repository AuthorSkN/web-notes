package com.webnotes.data.dao;

import com.webnotes.data.dao.adapters.DBAdapter;
import com.webnotes.data.entity.Folder;

public final class FolderDAOImpl extends DAO<Folder> {

    public FolderDAOImpl(DBAdapter adapter) {
        super(adapter);
    }
}
