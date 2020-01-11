package com.nn.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;

import javax.jms.ConnectionFactory;
import javax.jms.Topic;

@Configuration
public class JMSTopicConfig {

    @Value("${topic.name}")
    private String topic_name;

    @Bean(name = "topic")
    public Topic topic() {
        return new ActiveMQTopic(topic_name);
    }

    /**
     * topic模式的ListenerContainer
     *
     * @return
     */
    @Bean(name = "topicListenerFactory")
    public DefaultJmsListenerContainerFactory topicListenerFactory(ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();

        /**
         *  true：主题模式
         *  false：队列模式
         */
        factory.setPubSubDomain(true);
        factory.setConnectionFactory(connectionFactory);
        return factory;
    }
}
