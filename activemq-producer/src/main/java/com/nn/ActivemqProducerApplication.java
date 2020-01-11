package com.nn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJms //开启 jms
@EnableScheduling // 开启 定时发送消息
public class ActivemqProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActivemqProducerApplication.class, args);
    }

}
