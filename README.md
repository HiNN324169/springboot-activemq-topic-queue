## springboot 整合 ActiveMQ

### 主要 ：消费者consumer 实现 同时监听 Queue 和 Topic
### 主要 ：生产者 定时 想 主题 和 队列发送消息

####版本说明
- springboot.version：2.2.2.RELEASE
- spring.version：5.2.2.RELEASE
- activemq.version：5.15.11
- java.version：1.8
- maven.version：3.3.9
- IDEA：2019.3.1

####项目说明
- 项目一：broker-service ：activemq-broker 容器(内嵌式 mq 服务，你也可以配置单独的 activemq 服务)
    - 主要提供 mq 服务
- 项目二：activemq-consumer : 消费者
- 项目三：activemq-producer ：生产者

---
### 项目一：broker-service ：activemq-broker
- 内嵌 activemq broker 
- 测试前 先启动内嵌 broker



### 项目二 重点 ：activemq-consumer : 消费者

##### 1、配置mq：application-dev.yml
````angular2
spring:
  activemq:
    broker-url: tcp://192.168.1.107:61616
    password: admin
    user: admin
    pool:
      max-connections: 100
  jms:
    pub-sub-domain: true

queue.name: boot-queue # 自定义一个队列名称（取数据：@Value("${queue.name}")）
topic.name: boot-topic # 自定义一个队列名称（取数据：@Value("${topic.name}")）
````

##### 2、重点 配置监听器
- 说明：在springboot 融合 activemq 时，监听器 是监听 主题 还是 队列 时通过 修改 配置 中 jms:pub-sub-domain 的参数
实现的。而默认是监听 queue 队列的。
    -   true：监听主题（topic），false：监听队列（queue）
- 若要 同时 监听主题 和 队列就要进行以下配置
- 在配置监听器的时，我们使用到一个注解
    - @JmsListener
- 在springboot 中 监听器 就是通过该注解实现的
- 需要注意的时 该注解对应接口内的 几个 重要 属性
    - destination = "队列/主题名称",
    - containerFactory：这个属性 对应着一个类：DefaultJmsListenerContainerFactory
        - 我们需要重新配置 DefaultJmsListenerContainerFactory 这个类
        - 通过该类 修改 上诉中：jms:pub-sub-domain 对应的值，setPubSubDomain(true)，实现监听主题
        - containerFactory 属性值 对应着 DefaultJmsListenerContainerFactory 类 @Bean 的 name值
- 片段代码如下：
 ```angular2
// 监听器
@Component
public class TopicListenerService {

    @JmsListener(destination = "${topic.name}",containerFactory = "topicListenerFactory")
    public void queueListener(TextMessage textMessage) throws JMSException {
        System.out.println(".......收到的 topic 消息为：" + textMessage.getText());
    }
}
```

```angular2
// 配置类
/**
     * queue模式的ListenerContainer
     *
     * @return
     */
    @Bean(name = "queueListenerFactory")
    public DefaultJmsListenerContainerFactory queueListenerFactory(ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        // 设置 监听 主题
        factory.setPubSubDomain(false);
        factory.setConnectionFactory(connectionFactory);
        return factory;
    }
```


### 项目三 重点：activemq-producer ：生产者

#### 发送消息
- 生产者 发送消息 可以使用 以下两个对象实现：
    - jmsMessagingTemplate.convertAndSend(topic/queue,object); 
    - jmsTemplate.convertAndSend(new ActiveMQTopic(topic_name),msg);
    
#### 定时发送消息
- 使用注解：@Scheduled
- 参数：fixedDelay 延迟时间：毫秒
````$xslt
/**
     *  实现 延迟 定时发送消息
     *
     */
    @SuppressWarnings("deprecation")
    @Scheduled(fixedDelay = 2000)
    public void producerMsgScheduled(){
        jmsMessagingTemplate.convertAndSend(queue,"Scheduled："+ new Date().toLocaleString());
        System.out.println("消息发送成功...");
    }
````

在springboot 启动类上 启用 定时发送
```$xslt
@EnableScheduling
```