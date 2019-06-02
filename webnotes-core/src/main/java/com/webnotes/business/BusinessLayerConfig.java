package com.webnotes.business;

import com.webnotes.data.dao.*;
import com.webnotes.data.entity.Action;
import com.webnotes.data.entity.Group;
import com.webnotes.data.entity.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;

@Configuration
public class BusinessLayerConfig {

    @Autowired
    private ApplicationContext applicationContext;

    private DAOFactory daoFactory = new DAOFactory(DAOFactory.HIBERNATE_ADAPTER);

    @Bean
    @SessionScope
    public NoteDAOImpl getNoteDataAccessor() {
        return daoFactory.createNoteDAO();
    }

    @Bean
    @SessionScope
    public GroupDAOImpl getGroupDataAccessor() {
        return daoFactory.createGroupDAO();
    }

    @Bean
    @SessionScope
    public ActionDAOImpl getActionDataAccessor() {
        return daoFactory.createActionDAO();
    }

    @Bean
    @SessionScope
    public GlobalSearchOperation getGlobalSearchOperation() {
        return new GlobalSearchOperationImpl();
    }

}
