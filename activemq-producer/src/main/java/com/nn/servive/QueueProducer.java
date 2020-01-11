package com.nn.servive;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.Date;

@Component
public class QueueProducer {

    public static final String SEND_SUCCESS = "1";
    public static final String SEND_FAIL = "0";

    @Autowired
    private JmsTemplate jmsTemplate;

    private String msg;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Value("${queue.name}")
    private String queue_name;

    @SuppressWarnings("deprecation")
    public String sendQueueMsg() {
        try {
            jmsTemplate.convertAndSend(new ActiveMQQueue(queue_name), msg);

//            jmsTemplate.setDefaultDestination(new ActiveMQQueue(queue_name));
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
    @Scheduled(fixedDelay = 3000)
    public void sendAutoQueue() {
        jmsTemplate.convertAndSend(new ActiveMQQueue(queue_name), "queue 自动发送消息："+new Date().toLocaleString());
    }
}
