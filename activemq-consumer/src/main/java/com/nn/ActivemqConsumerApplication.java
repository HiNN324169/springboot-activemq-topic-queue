package com.nn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms // 开启 jms
public class ActivemqConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActivemqConsumerApplication.class, args);
    }

}
