package com.webnotes.business;

import org.springframework.stereotype.Service;

import javax.jms.*;
import java.util.Map;

@Service
public class WebNoteMessageListener implements MessageListener {
    public void onMessage(Message message) {
        Map map = null;
        try {
            map = (Map) ((MapMessage) message).getObject("map");
            System.out.println(map.get("name"));
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
