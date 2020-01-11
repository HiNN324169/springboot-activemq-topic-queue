package com.nn.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;

@Configuration
public class JMSQueueConfig {

    @Value("${queue.name}")
    private String queue_name;

    @Bean(name = "queue")
    public Queue queue() {
        return new ActiveMQQueue(queue_name);
    }

    /**
     * queue模式的ListenerContainer
     *
     * @return
     */
    @Bean(name = "queueListenerFactory")
    public DefaultJmsListenerContainerFactory queueListenerFactory(ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setPubSubDomain(false);
        factory.setConnectionFactory(connectionFactory);
        return factory;
    }
}
