package com.nn.controller;

import com.nn.servive.QueueProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QueueController {

    @Autowired
    private QueueProducer queueProducer;

    @RequestMapping("/queue/{msg}")
    public Object queue(@PathVariable("msg") String msg){
        queueProducer.setMsg(msg);
        return queueProducer.sendQueueMsg();
    }
}
