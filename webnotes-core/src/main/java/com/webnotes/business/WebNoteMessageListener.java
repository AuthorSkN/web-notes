package com.webnotes.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jms.*;
import java.util.Map;

@Service
public class WebNoteMessageListener implements MessageListener {

    @Autowired
    private WebNoteEmailSender emailSender;

    public void onMessage(Message message) {
        Map map = null;
        try {
            map = (Map) ((MapMessage) message).getObject("map");
            String type = map.get("type").toString();
            String operation = map.get("operation").toString();
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
        System.out.println(operation + " note" + note.get("id"));
        emailSender.sendEmail(operation + " note" + note.get("id"));
    }

    private void logGroup(Map group, String operation){
        System.out.println(operation + " group" + group.get("id"));
    }

    private void logAction(Map action, String operation){
        System.out.println(operation + " action" + action.get("id"));
    }
}
