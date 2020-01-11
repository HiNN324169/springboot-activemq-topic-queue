package com.nn.servive;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.Date;

@Component
public class TopicProducer {

    public static final String SEND_SUCCESS = "1";
    public static final String SEND_FAIL = "0";

    @Autowired
    private JmsTemplate jmsTemplate;

    private String msg;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Value("${topic.name}")
    private String topic_name;

    @SuppressWarnings("deprecation")
    public String sendTopicMsg() {
        try {
            jmsTemplate.convertAndSend(new ActiveMQTopic(topic_name),msg);

//            jmsTemplate.setDefaultDestination(new ActiveMQTopic(topic_name));
//            jmsTemplate.send(new MessageCreator() {
//                @Override
//                public Message createMessage(Session session) throws JMSException {
//                    return session.createTextMessage(msg);
//                }
//            });
            return SEND_SUCCESS;
        } catch (Exception e) {
            return SEND_FAIL;
        }
    }

    @SuppressWarnings("deprecation")
    @Scheduled(fixedDelay = 2000)
    public String autoSendTopic(){
        try {
            jmsTemplate.convertAndSend(new ActiveMQTopic(topic_name),"topic自动发送消息："+new Date().toLocaleString());
            return SEND_SUCCESS;
        } catch (Exception e) {
            return SEND_FAIL;
        }
    }
}
