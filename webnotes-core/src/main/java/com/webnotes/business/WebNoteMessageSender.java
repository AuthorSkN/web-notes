package com.webnotes.business;

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

    public void send(Note note) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", note.getId());
        map.put("name", note.getName());
        jmsTemplate.convertAndSend(map);
    }
}
