package com.anmol.queue.service;


import com.anmol.queue.Exception.TopicNotFoundException;
import com.anmol.queue.model.Message;
import com.anmol.queue.model.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {
    private final TopicService topicService;

    @Autowired
    public ProducerService(TopicService topicService) {
        this.topicService = topicService;
    }

    public void produce(String topicName, Message message) {
        Topic topic = topicService.getTopic(topicName);
        if (topic != null) {
            topic.addMessage(message);
        } else {
            throw new TopicNotFoundException("Topic not found");
        }
    }
}
