package com.nn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import javax.jms.Topic;

@Component
public class TopicListenerService {

    @JmsListener(destination = "${topic.name}",containerFactory = "topicListenerFactory")
    public void queueListener(TextMessage textMessage) throws JMSException {
        System.out.println(".......收到的 topic 消息为：" + textMessage.getText());
    }
}
