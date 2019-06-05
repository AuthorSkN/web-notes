package com.webnotes.business;

import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import java.util.Map;

@Component
public class WebNoteMessageConverter implements MessageConverter {

    public Message toMessage(Object object, Session session) throws JMSException, MessageConversionException {
        Map map = (Map) object;
        MapMessage message = session.createMapMessage();
        message.setObject("map", map);
        return message;
    }

    public Object fromMessage(Message message) throws JMSException, MessageConversionException {
        return message;
    }
}
