package com.anmol.queue.service;


import com.anmol.queue.Exception.ConsumerNotFoundException;
import com.anmol.queue.Exception.TopicNotFoundException;
import com.anmol.queue.model.Consumer;
import com.anmol.queue.model.Message;
import com.anmol.queue.model.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {
    private final TopicService topicService;

    @Autowired
    public ConsumerService(TopicService topicService) {
        this.topicService = topicService;
    }

    public Message consume(String topicName, String consumerId) {
        Topic topic = topicService.getTopic(topicName);
        if (topic != null) {
            if (topic.isConsumerSubscribed(consumerId)) {
                return topic.getNextMessageForConsumer(consumerId);
            } else {
                throw new ConsumerNotFoundException("Consumer not subscribed to topic");
            }
        } else {
            throw new TopicNotFoundException("Topic not found");
        }
    }

    public void subscribe(String topicName, String consumerId) {
        Consumer consumer = new Consumer(consumerId, topicName);
        topicService.subscribeConsumer(topicName, consumer);
    }
}
