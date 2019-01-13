package com.webnotes.business;

import com.webnotes.data.dao.DAO;
import com.webnotes.data.dao.DAOFactory;
import com.webnotes.data.entity.Action;
import com.webnotes.data.entity.Group;
import com.webnotes.data.entity.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BusinessLayerConfig {

    @Autowired
    private ApplicationContext applicationContext;

    private DAOFactory daoFactory = new DAOFactory(DAOFactory.HIBERNATE_ADAPTER);

    @Bean
    public DAO<Note> getNoteDataAccessor() {
        return daoFactory.createNoteDAO();
    }

    @Bean
    public DAO<Group> getGroupDataAccessor() {
        return daoFactory.createGroupDAO();
    }

    @Bean
    public DAO<Action> getActionDataAccessor() {
        return daoFactory.createActionDAO();
    }

    @Bean
    public GlobalSearchOperation getGlobalSearchOperation() {
        return new GlobalSearchOperationImpl();
    }

}
