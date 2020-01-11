package com.nn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.TextMessage;

@Component
public class QueueListenerService {

    @JmsListener(destination = "${queue.name}",containerFactory = "queueListenerFactory")
    public void queueListener(TextMessage textMessage) throws JMSException {
        System.out.println("收到的 queue 消息为："+textMessage.getText());
    }
}
