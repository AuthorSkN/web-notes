package com.webnotes.business;

import com.webnotes.data.dao.LogDAOImpl;
import com.webnotes.data.dao.adapters.HibernateAdapter;
import com.webnotes.data.entity.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.jms.*;
import java.util.Map;

@Lazy
@Service
public class WebNoteMessageListener implements MessageListener {

    @Autowired
    private WebNoteEmailSender emailSender;

    private LogDAOImpl loggingDataAccessor;

    public void onMessage(Message message) {
        Map map = null;
        try {
            map = (Map) ((MapMessage) message).getObject("map");
            String type = map.get("type").toString();
            String operation = map.get("operation").toString();
            loggingDataAccessor = new LogDAOImpl(new HibernateAdapter());
            switch (type) {
                case "note": logNote(map, operation); break;
                case "group": logGroup(map, operation); break;
                case "action": logAction(map, operation); break;
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private void logNote(Map note, String operation){
        String logText = "'" + note.get("id") + " " + note.get("name") + " " +
                note.get("parent") + " " + note.get("text") + "'";
        //emailSender.sendEmail(operation + " note" + note.get("id"));
        System.out.println("Log:" + logText);
        loggingDataAccessor.add(new Log("note", operation, logText));

    }

    private void logGroup(Map group, String operation){
        String logText = "'" + group.get("id") + " " + group.get("name") + "'";
        System.out.println("Log:" + logText);
        loggingDataAccessor.add(new Log("group", operation, logText));
    }

    private void logAction(Map action, String operation){
        String logText = "'" + action.get("id") + " " + action.get("text") + " " +
                action.get("passed") + "'";
        System.out.println("Log:" + logText);
        loggingDataAccessor.add(new Log("action", operation, logText));
    }
}
