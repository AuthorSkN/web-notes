package com.webnotes.business;

import com.webnotes.data.dao.*;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.web.context.annotation.SessionScope;

import java.util.Properties;

@Configuration
@ComponentScan(basePackageClasses = GlobalSearchOperationImpl.class)
public class BusinessLayerConfig {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private WebNoteMessageConverter messageConverter;

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

    //----------------------JMS

    @Bean
    public ActiveMQConnectionFactory connectionFactory(){
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        return connectionFactory;
    }

    @Bean
    @Autowired
    public DefaultMessageListenerContainer listenerContainer(WebNoteMessageListener messageListener){
        DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.setDestinationName("web_notes");
        container.setMessageListener(messageListener);
        return container;
    }

    @Bean
    public JmsTemplate jmsTemplate(){
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory());
        jmsTemplate.setMessageConverter(messageConverter);
        jmsTemplate.setDefaultDestinationName("web_notes");
        return jmsTemplate;
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setProtocol("smtp");
        mailSender.setUsername("author.skn@gmail.com");
        mailSender.setPassword("%tPovdis61");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }

}
