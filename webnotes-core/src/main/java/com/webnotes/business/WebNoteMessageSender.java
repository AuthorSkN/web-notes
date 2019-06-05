package com.webnotes.business;

import com.webnotes.data.entity.Action;
import com.webnotes.data.entity.Group;
import com.webnotes.data.entity.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class WebNoteMessageSender {

    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendNoteOperation(Note note, String operation) {
        Map<String, Object> map = new HashMap<>();
        map.put("operation", operation);
        map.put("id", note.getId());
        map.put("name", note.getName());
        map.put("parent", note.getGroup());
        map.put("text", note.getText());
        map.put("type", "note");
        jmsTemplate.convertAndSend(map);
    }

    public void sendGroupOperation(Group group, String operation) {
        Map<String, Object> map = new HashMap<>();
        map.put("operation", operation);
        map.put("id", group.getId());
        map.put("name", group.getName());
        map.put("type", "group");
        jmsTemplate.convertAndSend(map);
    }

    public void sendActionOperation(Action action, String operation) {
        Map<String, Object> map = new HashMap<>();
        map.put("operation", operation);
        map.put("id", action.getId());
        map.put("text", action.getText());
        map.put("passed", action.getPassed());
        map.put("type", "action");
        jmsTemplate.convertAndSend(map);
    }
}
