package com.nn.controller;

import com.nn.servive.QueueProducer;
import com.nn.servive.TopicProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TopicController {

    @Autowired
    private TopicProducer topicProducer;

    @RequestMapping("/topic/{msg}")
    public Object topic(@PathVariable("msg") String msg){
        topicProducer.setMsg(msg);
        return topicProducer.sendTopicMsg();
    }
}
